package com.yunjuanyunshu.modules.web.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.UserService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.common.util.RespInfoUtil;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
@Component
@ClassAnnot("system.user")
public class UserController {

    private static UserService userService;

    /**
     * 静态注入service
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(UserService userService) {
        UserController.userService = userService;
    }

    /**
     * Delete boolean.
     *
     * @param user the user
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(User user){
        return userService.deleteById(user);
    }

    /**
     * Insert or update boolean.
     *
     * @param user the user
     * @return the boolean
     */
    @MethodAnnot
    public static void insertOrUpdate(User user,RespInfo respInfo){
       userService.insertOrupdate(user,respInfo);
    }

    /**
     * get User
     *
     * @param user
     * @param paraRespInfo
     */
    @MethodAnnot
    public static void get(User user, RespInfo paraRespInfo){
        user = userService.selectById(user);
        String busMsg = "不存在此id";
        ServiceErrorCodeEnum seEnum = ServiceErrorCodeEnum.ERROR;
        if(user != null) {
            busMsg = "查询成功";
            seEnum = ServiceErrorCodeEnum.SUCCESS;
        }
        RespInfoUtil.businessError(userService.selectById(user), busMsg,
                seEnum, paraRespInfo);
    }

    /**
     * Get user.
     *
     * @param user the user
     * @return the user
     */
    @MethodAnnot
    public static List<User> getByName(User user){
        EntityWrapper<User> ew = new EntityWrapper<User>();
        ew.setEntity(user);
        return  userService.selectList(ew);

    }

    /**
     * 检查登录.
     *
     * @return void
     */
    @MethodAnnot
    public static void login(HttpServletRequest request, String loginName, String password, RespInfo paraRespInfo) {
        userService.login(request, loginName, password, paraRespInfo);
    }
    /**
     * 退出登录.
     *
     * @return void
     */
    @MethodAnnot
    public static void logout(HttpServletRequest request, RespInfo paraRespInfo) {
        userService.logout(request ,paraRespInfo);
    }

    /**
     * 注册
     * @param user
     * @param respInfo
     */
    @MethodAnnot
    public static void register(User user, RespInfo respInfo) {
        userService.register(user, respInfo);
    }

    /**
     * 更新个人信息
     * @param user
     * @param paraRespInfo
     */
    @MethodAnnot
    public static void updateUserInfo(User user, RespInfo paraRespInfo) {
        userService.updateUserInfo(user, paraRespInfo);
    }

    /**
     * 修改密码时检查输入密码是否与原密码一致
     * @param password
     * @param user
     * @param paraRespInfo
     */
    @MethodAnnot
    public static void passwordCheck(String password, User user, RespInfo paraRespInfo) {
        userService.passwordCheck(password, user, paraRespInfo);
    }

    @MethodAnnot
    public static void passwordCheckAndChange(String passOld, String passNew, User user,RespInfo respInfo){
        userService.passwordCheckAndChange(passOld,passNew,user,respInfo);
    }

    /**
     * 检查手机号是否已被注册
     * @param phone
     * @param paraRespInfo
     */
    @MethodAnnot
    public static void phoneCheck(String phone, RespInfo paraRespInfo) {
        userService.phoneCheck(phone, paraRespInfo);
    }

    /**
     * 检查用户名是否存在
     * @param loginName
     * @param paraRespInfo
     */
    @MethodAnnot
    public static void loginNameCheck(String loginName, RespInfo paraRespInfo){
        userService.loginNameCheck(loginName, paraRespInfo);
    }

    /**
     * Gets user by page.
     *
     * @return the user by page
     */
    @MethodAnnot
    public static Page<User> getUserByPage(Page<User> page) {
        return userService.selectPage(page,
                new EntityWrapper<User>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
    @MethodAnnot
    public static List<User> getUserByPageList(Page<User> page) {

        return userService.getUserByPage(page);
    }

    /**
     * Gets personal overview.
     *
     * @param user     the user
     * @param respInfo the resp info
     */
    @MethodAnnot
    public static void getPersonalOverview(User user,RespInfo respInfo) {

         userService.getPersonalOverview(user,respInfo);
    }

    /**
     * 获取用户相关的院校信息
     * @param user
     * @param respInfo
     */
    @MethodAnnot
    public static void getCollegeInfo(User user, RespInfo respInfo) {
        userService.getCollegeInfo(user,respInfo);
    }
}
