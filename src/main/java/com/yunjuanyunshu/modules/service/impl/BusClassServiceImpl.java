package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.BusClassDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.modules.util.RandomCodeUtils;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qch
 * @since 2017-10-27
 */
@Service
public class BusClassServiceImpl extends ServiceImpl<BusClassDao, BusClass> implements BusClassService {

    @Autowired
    private BusClassService busClassService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private BusCourseRecordService busCourseRecordService;

    @Autowired
    private BusCourseService busCourseService;

    @Autowired
    private BusChapterService busChapterService;

    @Autowired
    private VwClassInfoService vwClassInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private BusClassDao busClassDao;

    @Override
    public void getClass(User user, Page<BusClass> page, RespInfo paraRespInfo) {
        try {
            Role role = roleService.selectById(userRoleService.selectOne(new EntityWrapper<UserRole>().eq("user_id", user.getId())).getRoleId());
            if (role == null) {
                throw new Exception(ServiceErrorCodeEnum.ERROR.getErrorStr());
            }

            Page<BusClass> busClassPage;

            if ("admin".equals(role.getEnname())) {
                EntityWrapper<BusClass> ew = new EntityWrapper<BusClass>();
                busClassPage = super.selectPage(page,
                        ew.orderBy(page.getOrderByField(),
                                page.isAsc()));
            } else {
                EntityWrapper<BusClass> ew = new EntityWrapper<BusClass>();
                busClassPage = super.selectPage(page,
                        ew.eq("create_by", user.getId()).orderBy(page.getOrderByField(),
                                page.isAsc()));
            }
            paraRespInfo.setValue(busClassPage);
            paraRespInfo.setMsg("查询成功");
            paraRespInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());

        } catch (Exception ex) {
            paraRespInfo.setError(ex.getMessage());
            paraRespInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void insertOrUpdateClass(BusClass busClass, RespInfo respInfo) {
        try {
            if (StringUtils.isBlank(busClass.getId())) {
                busClass.setCreateDate(new Date());
                respInfo.setMsg("插入班级信息成功");
            } else {
                respInfo.setMsg("更新班级信息成功");
            }

            long b_times = busClass.getBeginDate().getTime();
            long f_times = busClass.getFinishDate().getTime();
            if (b_times < f_times) {
                busClass.setName(Base64Util.urlDecode(busClass.getName()));
                busClass.setUpdateDate(new Date());
                super.insertOrUpdate(busClass);
                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            } else {
                respInfo.setMsg("开班时间必须小于结束时间");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            }

        } catch (Exception ex) {
            respInfo.setMsg("操作失败");
            respInfo.setError(ex.getMessage());
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void getClassesByCourse(BusCourse busCourse, RespInfo respInfo) {
        try {
            List<BusClass> busClasses = super.selectList(new EntityWrapper<BusClass>()
                    .setSqlSelect("id,name")
                    .eq("course_id", busCourse.getId()));
            respInfo.setValue(busClasses);
            respInfo.setMsg("获取该课程所开的班级信息成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setMsg("操作失败");
            respInfo.setError(ex.getMessage());
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void hasClass(BusCourse busCourse, RespInfo respInfo) {
        try {
            int count = busClassService.selectCount(new EntityWrapper<BusClass>()
                    .eq("course_id", busCourse.getId()));
            if (count > 0) {
                respInfo.setMsg("该门课程有班级存在");
                respInfo.setCode(0);
            } else {
                respInfo.setMsg("该门课程不存在班级");
                respInfo.setCode(1);
            }
        } catch (Exception ex) {
            respInfo.setMsg("操作失败");
            respInfo.setError(ex.getMessage());
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void isJoinClass(User user, BusCourse busCourse, RespInfo respInfo) {
        try {
            System.out.println(user.getLoginName());
            System.out.println(busCourse.getTitle());
            BusCourseRecord busCourseRecord = busCourseRecordService.selectOne(new EntityWrapper<BusCourseRecord>().eq("user_id", user.getId()).eq("course_id", busCourse.getId()));
            if (busCourseRecord != null) {
                if ("0".equals(busCourseRecord.getClassId())) {
                    respInfo.setMsg("该用户还未加入班级");
                    respInfo.setCode(0);
                } else {
                    respInfo.setMsg("该用户已加入班级");
                    VwClassInfo vwClassInfo = vwClassInfoService.selectOne(new EntityWrapper<VwClassInfo>()
                            .setSqlSelect("id,class_name AS className,create_name AS createName")
                            .eq("id", busCourseRecord.getClassId()));
                    respInfo.setValue(vwClassInfo);
                    respInfo.setCode(1);
                }
            } else {
                respInfo.setMsg("该用户还未学习过这门课程呢");
                respInfo.setCode(0);
            }
        } catch (Exception ex) {
            respInfo.setMsg("操作失败");
            respInfo.setError(ex.getMessage());
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void joinClass(User user, BusCourse busCourse, BusClass busClass, RespInfo respInfo) {
        try {
            BusClass aBusClass = super.selectById(busClass);
            if (aBusClass.getInvitationCode().equals(busClass.getInvitationCode())) {
                //检查用户真实姓名是否存在
                User result = userService.selectById(user.getId());
                if (result.getName() != null && !"".equals(result.getName())) {

                    BusCourseRecord busCourseRecord = busCourseRecordService.selectOne(new EntityWrapper<BusCourseRecord>()
                            .eq("user_id", user.getId())
                            .eq("course_id", busCourse.getId()));
                    if (busCourseRecord != null) {
                        busCourseRecord.setClassId(busClass.getId());
                    } else {
                        busCourseRecord = new BusCourseRecord();
                        busCourseRecord.setCourseId(busCourse.getId());
                        busCourseRecord.setUserId(user.getId());
                        busCourseRecord.setClassId(busClass.getId());
                        busCourseRecord.setCreateTime(new Date());
                        //busCourse的focus字段加1
                        BusCourse bsCourse = busCourseService.selectById(busCourse.getId());
                        int focus = Integer.parseInt(bsCourse.getFocus()) + 1;
                        busCourse.setFocus(String.valueOf(focus));
                        busCourseService.insertOrUpdate(busCourse);
                        //获取该课程的子章节列表的第一个章节
                        //先找到第一父章节
                        BusChapter pBusChapter = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                                .eq("course_id", busCourse.getId())
                                .eq("parent_id", "")
                                .orderBy("sort", true));
                        BusChapter cBusChpater = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                                .eq("parent_id", pBusChapter.getId())
                                .orderBy("sort", true));
                        busCourseRecord.setLastest(cBusChpater.getId());
                    }
                    busCourseRecordService.insertOrUpdate(busCourseRecord);
                    respInfo.setMsg("加入班级成功");
                    respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                }else {
                    respInfo.setMsg("用户姓名不存在，请先完善个人信息");
                    respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                }
            } else {
                respInfo.setMsg("邀请码错误");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            }
        } catch (Exception ex) {
            respInfo.setMsg("操作失败");
            respInfo.setError(ex.getMessage());
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void getBusCourseRecordByClassId(BusClass busClass, RespInfo respInfo) {
        try {
            List<BusCourseRecord> busCourseRecords = busCourseRecordService.selectList(new EntityWrapper<BusCourseRecord>()
                    .setSqlSelect("id,user_id AS userId,lastest,completion")
                    .eq("class_id", busClass.getId()));
            respInfo.setMsg("获取该班级学生学习进度信息成功");
            respInfo.setValue(busCourseRecords);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setMsg("操作失败");
            respInfo.setError(ex.getMessage());
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void quitClass(BusCourseRecord busCourseRecord, RespInfo respInfo) {
        try {
            busCourseRecord = busCourseRecordService.selectOne(new EntityWrapper<BusCourseRecord>()
                    .eq("id", busCourseRecord.getId()));
            busCourseRecord.setClassId("0");
            busCourseRecordService.insertOrUpdate(busCourseRecord);
            respInfo.setMsg("该学生成功退出该班级");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setMsg("操作失败");
            respInfo.setError(ex.getMessage());
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void deleteClass(BusClass busClass, RespInfo respInfo) {
        try {
            BusCourseRecord busCourseRecord = new BusCourseRecord();
            busCourseRecord.setClassId("0");
            busCourseRecordService.update(busCourseRecord, new EntityWrapper<BusCourseRecord>()
                    .eq("class_id", busClass.getId()));
            super.deleteById(busClass);
            respInfo.setMsg("关闭班级成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setMsg("操作失败");
            respInfo.setError(ex.getMessage());
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void getClassUUId(User user, RespInfo respInfo) {
        int count = userService.selectCount(new EntityWrapper<User>().eq("id", user.getId()));
        if (count > 0) {
            String uuid = RandomCodeUtils.generateShortUuid();
            respInfo.setValue(uuid);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("获取班级唯一邀请码成功");
        } else {
            respInfo.setCode(ServiceErrorCodeEnum.StudentNotFoundError.getErrorCode());
            respInfo.setMsg("用户名未找到");
        }
    }

    @Override
    public void getClassesWithName(Page<BusClass> page, HttpServletRequest request, RespInfo respInfo) {
        try {
            EntityWrapper<BusClass> ew=new EntityWrapper<BusClass>();
            User user = UserUtil.getUser(request.getHeader("token"),respInfo);
            UserRole userRole = userRoleService.selectOne(new EntityWrapper<UserRole>().eq("user_id",user.getId()));
            if (userRole == null) {
                respInfo.setMsg("用户无权限");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                return;
            }
            Role role = roleService.selectOne(new EntityWrapper<Role>().eq("id",userRole.getRoleId()));
            if ("teacher".equals(role.getEnname())){
                ew.eq("c.create_by",user.getId());
            }
            if("user".equals(role.getEnname())) {
                return;
            }
            page.setRecords(busClassDao.selectBusClassesWithName(page,ew));
            respInfo.setValue(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}