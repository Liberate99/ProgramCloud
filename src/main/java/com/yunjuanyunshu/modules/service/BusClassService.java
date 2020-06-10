package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusClass;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.BusCourseRecord;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qch
 * @since 2017-10-27
 */
public interface BusClassService extends IService<BusClass> {

        void getClass(User user, Page<BusClass> page, RespInfo paraRespInfo);
        void hasClass(BusCourse busCourse, RespInfo respInfo);
        void insertOrUpdateClass(BusClass busClass, RespInfo respInfo);
        void isJoinClass(User user, BusCourse busCourse, RespInfo respInfo);
        void joinClass(User user, BusCourse busCourse, BusClass busClass, RespInfo respInfo);
        void getClassesByCourse(BusCourse busCourse, RespInfo respInfo);
        void getClassesByCourseCreatedByUser(User user, BusCourse busCourse, RespInfo respInfo);
        void getBusCourseRecordByClassId(BusClass busClass, RespInfo respInfo);
        void quitClass(BusCourseRecord busCourseRecord, RespInfo respInfo);
        void deleteClass(BusClass busClass, RespInfo respInfo);
        void getClassUUId(User user, RespInfo respInfo);
        void getClassesWithName(Page<BusClass>  page, HttpServletRequest request, RespInfo respInfo);
}