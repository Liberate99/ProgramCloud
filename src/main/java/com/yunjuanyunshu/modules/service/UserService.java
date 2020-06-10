package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.User;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-06-30
 */
public interface UserService extends IService<User> {

    List<User> getUserByPage(Page<User> page);
    void login(HttpServletRequest request,String loginName, String password, RespInfo paraRespInfo);
    void logout(HttpServletRequest request, RespInfo paraRespInfo);
    void register(User user, RespInfo respInfo);
    void updateUserInfo(User user, RespInfo paraRespInfo);
    void passwordCheck(String password, User user, RespInfo paraRespInfo);
    void phoneCheck(String phone, RespInfo paraRespInfo);
    void loginNameCheck(String loginName, RespInfo paraRespInfo);
    void insertOrupdate(User user,RespInfo respInfo);
    void getPersonalOverview(User user,RespInfo respInfo);
    void passwordCheckAndChange(String passOld, String passNew, User user,RespInfo respInfo);
    void getCollegeInfo(User user, RespInfo respInfo);
//    boolean checkAdminRole(User user);
}
