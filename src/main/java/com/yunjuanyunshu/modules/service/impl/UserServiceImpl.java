package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.common.Const;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.UserDao;
import com.yunjuanyunshu.modules.service.*;
import com.yunjuanyunshu.modules.util.CacheUtils;
import com.yunjuanyunshu.modules.util.IdGen;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.common.util.AES;
import com.yunjuanyunshu.yunfd.common.util.RespInfoUtil;
import com.yunjuanyunshu.yunfd.common.util.StringUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.yunjuanyunshu.yunfd.common.util.AES.decyptKey;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {


    @Autowired
    private UserDao dao;
    @Autowired
    private BusCourseRecordService busCourseRecordService ;
    @Autowired
    private BusCourseService busCourseService ;
    @Autowired
    private BusStudentInfoService busStudentInfoService;
    @Autowired
    private BusTeacherInfoService busTeacherInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<User> getUserByPage(Page<User> page) {
       return  dao.selectPage(page, new EntityWrapper<User>().orderBy(page.getOrderByField(), page.isAsc()));
    }

    /**
     * 登录验证
     * @param loginName
     * @param password
     * @param paraRespInfo
     */
    @Override
    public void login(HttpServletRequest request,String loginName, String password, RespInfo paraRespInfo) {
        String msg;
        ServiceErrorCodeEnum serviceErrorCodeEnum;
        User userRes = null;
        //校验是否存在该用户
        User existUser = super.selectOne(new EntityWrapper<User>().eq("login_name", loginName));
        //登录前先把过去token清掉
        if(existUser != null && CacheUtils.get(existUser.getToken()) != null){
            CacheUtils.remove(existUser.getToken());
        }
        msg = "登录失败";
        serviceErrorCodeEnum = ServiceErrorCodeEnum.UserNameOrPasswordError;

        try {
            //用户密码加密
            String ciphertext;
            ciphertext = AES.encryptAES(password, decyptKey);
            if (existUser != null && existUser.getPassword().equals(ciphertext)) {
                //生成token
                String token = IdGen.uuid();
                existUser.setToken(token);
                //修改登录时间
                existUser.setLoginDate(new Date());
                //更新到数据库
                boolean flag  = super.updateById(existUser);

                if (flag) {
                    msg = "登录成功";
                    serviceErrorCodeEnum = ServiceErrorCodeEnum.SUCCESS;
                    userRes = dao.getUserWithRoleTypeById(existUser.getId());
                    // 用户缓存
                    // session保存用户信息
                    request.getSession().setAttribute("token",userRes);
                    CacheUtils.put(token,userRes);
                }
            }
        } catch (Exception e) {
            serviceErrorCodeEnum = ServiceErrorCodeEnum.ERROR;
        }
        RespInfoUtil.businessError(userRes, msg, serviceErrorCodeEnum, paraRespInfo);
    }

    @Override
    public void logout(HttpServletRequest request, RespInfo paraRespInfo) {
        request.getSession().removeAttribute("token");
    }

    /**
     * 注册 user信息必备(用户名,密码,手机号,验证码)
     * @param user
     * @param respInfo
     */
    @Override
    public void register(User user, RespInfo respInfo) {
        String code = (String) CacheUtils.get(user.getLoginName() + "validateCode");
        int nameCount = super.selectCount(new EntityWrapper<User>().eq("login_name", user.getLoginName()));
        if (nameCount > 0) {
            respInfo.setCode(ServiceErrorCodeEnum.StudentNoIsRegistered.getErrorCode());
            respInfo.setError(ServiceErrorCodeEnum.StudentNoIsRegistered.getErrorStr());
            respInfo.setMsg("用户名已被注册");
            return;
        }
        int phoneCount = super.selectCount(new EntityWrapper<User>().eq("phone", user.getPhone()));
        if (phoneCount > 0) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ServiceErrorCodeEnum.ERROR.getErrorStr());
            respInfo.setMsg("手机已被注册");
            return;
        }
        if (!user.getValidateCode().toUpperCase().equals(code)) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("验证码不正确");
            return;
        }
        if (StringUtils.isBlank(user.getPassword()) && StringUtils.isBlank(user.getPhone())) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("密码或手机号码为空");
            return;
        }

        try {
            //用户密码加密
            String ciphertext;
            ciphertext = AES.encryptAES(user.getPassword(), decyptKey);
            user.setPassword(ciphertext);
            user.setCreateDate(new Date());
            user.setUpdateDate(user.getCreateDate());
            super.insert(user);
            user = super.selectOne(new EntityWrapper<User>().eq("login_name", user.getLoginName()));
            if (user == null) {
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                respInfo.setMsg("注册失败");
                return;
            }

        } catch (Exception e) {
            LOGGER.error("加密异常",e);
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("注册失败");
        }

        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setMsg("注册成功");
    }

    /**
     * 添加用户与角色相关联信息
     * @param user
     */
    private void insertUserRole(User user) {
        Role role = new Role();

        if (Const.Role.ROLE_USER.equals(user.getIdentityRole())) {
            role = roleService.selectOne(new EntityWrapper<Role>().eq("role_type",Const.Role.ROLE_USER));
        }
        if (Const.Role.ROLE_TEACHER.equals(user.getIdentityRole())) {
            role = roleService.selectOne(new EntityWrapper<Role>().eq("role_type",Const.Role.ROLE_TEACHER));
        }
        if (Const.Role.ROLE_ADMIN.equals(user.getIdentityRole())) {
            role = roleService.selectOne(new EntityWrapper<Role>().eq("role_type",Const.Role.ROLE_ADMIN));
        }
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(user.getId());
        userRoleService.insert(userRole);
    }



    /**
     * 更新个人信息(可进行头像,昵称,手机号,密码单项信息修改)
     * @param user
     * @param paraRespInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(User user, RespInfo paraRespInfo) {
        String msg;
        ServiceErrorCodeEnum serviceErrorCodeEnum;
        User userRes;
        userRes = super.selectOne(new EntityWrapper<User>().eq("token", user.getToken()));
        msg = "更新个人信息失败";
        serviceErrorCodeEnum = ServiceErrorCodeEnum.TokenIsOverdue;
        //todo 清除用户缓存
        if (userRes != null) {
            try {
                //头像更新
                if (user.getPhoto() != null && StringUtils.isNotBlank(user.getPhoto())) {
                    userRes.setPhoto(user.getPhoto());
                }
                //昵称更新
                if (user.getName() != null && StringUtils.isNotBlank(user.getName())) {
                    userRes.setName(user.getName());
                }
                //性别更新
                if (user.getGender() != null && StringUtils.isNotBlank(user.getGender())) {
                    userRes.setGender(user.getGender());
                }
                //手机号更新
                if (user.getPhone() != null && StringUtils.isNotBlank(user.getPhone())) {
                    userRes.setPhone(user.getPhone());
                }
                //密码更新
                if (user.getPassword() != null && StringUtils.isNotBlank(user.getPassword())) {
                    String ciphertext;
                    ciphertext = AES.encryptAES(user.getPassword(), decyptKey);
                    userRes.setPassword(ciphertext);
                }
                userRes.setUpdateDate(new Date());
                boolean flag = super.updateById(userRes);
                if (flag) {
                    msg = "更新个人信息成功";
                    serviceErrorCodeEnum = ServiceErrorCodeEnum.SUCCESS;
                }
            } catch (Exception e) {
                serviceErrorCodeEnum = ServiceErrorCodeEnum.ERROR;
            }

        }

        RespInfoUtil.businessError(userRes, msg, serviceErrorCodeEnum, paraRespInfo);
    }

    /**
     * 修改密码时检查输入密码是否与原密码一致
     * @param password
     * @param user
     * @param paraRespInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void passwordCheck(String password, User user, RespInfo paraRespInfo) {
        String msg;
        ServiceErrorCodeEnum serviceErrorCodeEnum;
        msg = "原密码输入错误";
        serviceErrorCodeEnum = ServiceErrorCodeEnum.OldPasswordError;
        //用户密码加密
        try {
            String ciphertext;
            ciphertext = AES.encryptAES(password, decyptKey);
            if (user.getPassword().equals(ciphertext)) {
                msg = "密码正确";
                serviceErrorCodeEnum = ServiceErrorCodeEnum.SUCCESS;
            }
        } catch (Exception e) {
            serviceErrorCodeEnum = ServiceErrorCodeEnum.ERROR;
        }

        RespInfoUtil.businessError(null, msg, serviceErrorCodeEnum, paraRespInfo);
    }

    /**
     * 检查旧密码并更改密码
     * @param passOld
     * @param passNew
     * @param user
     * @param respInfo
     */
    @Override
    public void passwordCheckAndChange(String passOld, String passNew, User user, RespInfo respInfo){
        try {
            User existUser = (User) UserUtil.getUser(user.getToken(),respInfo);
            User  currentUser= super.selectById(existUser.getId());
            String ciphertextOld;
            ciphertextOld = AES.encryptAES(passOld, decyptKey);
            if (currentUser.getPassword().equals(ciphertextOld)) {
                String ciphertextNew = AES.encryptAES(passNew, decyptKey);
                currentUser.setPassword(ciphertextNew);
                currentUser.setUpdateDate(new Date());
                super.updateById(currentUser);
                respInfo.setMsg("密码更新成功");
                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                respInfo.setValue(currentUser);
            } else {
                respInfo.setMsg("旧密码输入错误");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                respInfo.setValue(currentUser);
            }

        } catch (Exception ex) {
            respInfo.setError(ex.getMessage());
            respInfo.setCode(-1);
        }
    }


    /**
     * 检查手机号是否已被注册
     * @param phone
     * @param paraRespInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void phoneCheck(String phone, RespInfo paraRespInfo) {
        String msg;
        ServiceErrorCodeEnum serviceErrorCodeEnum;
        User existUser = super.selectOne(new EntityWrapper<User>().eq("phone", phone));

        if (existUser != null) {
            msg = "该手机号码已被注册";
            serviceErrorCodeEnum = ServiceErrorCodeEnum.PhoneNumberError;
        } else {
            msg = "手机号可用";
            serviceErrorCodeEnum = ServiceErrorCodeEnum.SUCCESS;
        }

        RespInfoUtil.businessError(null, msg, serviceErrorCodeEnum, paraRespInfo);
    }

    /**
     * 检查用户名是否存在
     * @param loginName
     * @param paraRespInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void loginNameCheck(String loginName, RespInfo paraRespInfo) {
        String msg;
        ServiceErrorCodeEnum serviceErrorCodeEnum;
        User existUser = super.selectOne(new EntityWrapper<User>().eq("login_name", loginName));

        if (existUser != null) {
            msg = "该账户已被注册";
            serviceErrorCodeEnum = ServiceErrorCodeEnum.StudentNoIsRegistered;
        } else {
            msg = "用户名可用";
            serviceErrorCodeEnum = ServiceErrorCodeEnum.SUCCESS;
        }

        RespInfoUtil.businessError(null, msg, serviceErrorCodeEnum, paraRespInfo);
    }

    @Override
    public void insertOrupdate(User user, RespInfo respInfo) {
        try {
            User existUser = super.selectOne(new EntityWrapper<User>().eq("login_name", user.getLoginName()));
            if(StringUtils.isNotBlank(user.getPassword())&& existUser==null){
                String   ciphertext = AES.encryptAES(user.getPassword(), AES.decyptKey);
                user.setPassword(ciphertext);
                user.setCreateDate(new Date());
                user.setUpdateDate(new Date());
            }else{
                user.setUpdateDate(new Date());
            }
            super.insertOrUpdate(user);
        }catch (Exception ex){
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ServiceErrorCodeEnum.ERROR.getErrorStr());
            respInfo.setValue(false);
        }
    }

    @Override
    public void getPersonalOverview(User user, RespInfo respInfo) {
        try {
        List<Object> map = new ArrayList<Object>();
        List<String> li_String = new ArrayList<String>();
        User existUser = super.selectOne(new EntityWrapper<User>().eq("token", user.getToken()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        SimpleDateFormat formatter_f = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter_t = new SimpleDateFormat("HH:mm");
       String createTime =  formatter.format(existUser.getCreateDate());
       String createTime_f =  formatter_f.format(existUser.getCreateDate());
       String createTime_t =  formatter_t.format(existUser.getCreateDate());
        li_String.add(createTime_f);
        li_String.add(existUser.getPhoto());
        li_String.add(createTime+"你开启了编程云之旅");
        map.add(li_String);
        int num = 0;
            Date afterTime = existUser.getCreateDate();
        List<BusCourseRecord> busCourseRecordList =  busCourseRecordService.selectList(new EntityWrapper<BusCourseRecord>().eq("user_id",existUser.getId()).orderBy("createTime",true));
                for(BusCourseRecord busCourseRecord:busCourseRecordList){
                    num+=1;
                    Calendar c = Calendar.getInstance();
                    c.setTime(afterTime);
                    // 今天+1天
                    c.add(Calendar.DAY_OF_MONTH, num);
                    List<String> temp_str = new ArrayList<String>();
                    temp_str.add(formatter_f.format(c.getTime()));
                    BusCourse busCourse = busCourseService.selectById(busCourseRecord.getCourseId());
                    temp_str.add(busCourse.getPic());
                    temp_str.add(formatter.format(busCourseRecord.getCreateTime())+"你选择了编程云的第"+num+"门课程");
                    map.add(temp_str);
                }
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setError(ServiceErrorCodeEnum.SUCCESS.getErrorStr());
            respInfo.setValue(map);
        }catch (Exception ex){
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ServiceErrorCodeEnum.ERROR.getErrorStr());
            respInfo.setValue(null);
        }
    }

    @Override
    public void getCollegeInfo(User user, RespInfo respInfo) {
        if("0".equals(user.getIdentityRole())) {
            BusStudentInfo busStudentInfo = busStudentInfoService.selectOne(new EntityWrapper<BusStudentInfo>()
                        .eq("user_id",user.getId()));
            if (busStudentInfo == null) {
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                respInfo.setMsg("您未填写院校信息,请完善");
                return;
            }
            respInfo.setValue(busStudentInfo);
            respInfo.setMsg("获取学生信息成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        }
        if ("1".equals(user.getIdentityRole())) {
            BusTeacherInfo busTeacherInfo = busTeacherInfoService.selectOne(new EntityWrapper<BusTeacherInfo>()
                        .eq("user_id",user.getId()));
            if (busTeacherInfo == null) {
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                respInfo.setMsg("您未填写院校信息,请完善");
                return;
            }
            respInfo.setValue(busTeacherInfo);
            respInfo.setMsg("获取教师信息成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        }
    }

//    @Override
//    public boolean checkAdminRole(User user) {
//        if ()
//    }
}
