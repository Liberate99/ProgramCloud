package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.BusTeacherInfo;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
public interface BusCourseService extends IService<BusCourse> {

        void focusCourse(User user,BusCourse busCourse,RespInfo respInfo);
        void myCourse(User user,Boolean hasFinished,RespInfo respInfo);
        void recommendedCourses(User user, int num, RespInfo respInfo);
        void getCourse(User user, Page<BusCourse> page, RespInfo paraRespInfo,Integer hasPublic,String courseStatus);
        void getFPGACourseCreateByMe(User user, Page<BusCourse> page, RespInfo paraRespInfo);
        void getTeacherInfo(BusTeacherInfo busTeacherInfo, RespInfo respInfo);
        void getCourseDetail(BusCourse busCourse, RespInfo respInfo);
        void insertWithTemplate(HttpSession session,BusCourse busCourse, RespInfo respInfo);
        void getAllCourse(HttpSession session, RespInfo respInfo);

}