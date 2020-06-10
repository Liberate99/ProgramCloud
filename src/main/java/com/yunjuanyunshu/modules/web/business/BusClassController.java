package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusClass;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.BusCourseRecord;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusClassService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qch
 * @since 2017-10-27
 */
@Component
@ClassAnnot("business.busClass")
public class BusClassController  {
        private static BusClassService busClassService;

        /**
         * 静态注入service
         *
         * @param busClassService
         */
        @Autowired
        public BusClassController (BusClassService busClassService){
            BusClassController.busClassService=busClassService;
        }

        /**
         * Delete boolean.
         *
         * @param busClass the busClass
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusClass busClass){
                return busClassService.deleteById(busClass);
        }

        /**
         * Insert or update boolean.
         *
         * @param busClass the busClass
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusClass busClass){
                return busClassService.insertOrUpdate(busClass);
        }

        /**
         * Get busClass.
         *
         * @param busClass the busClass
         * @return the busClass
         */
        @MethodAnnot
        public static BusClass get(BusClass busClass){
                return busClassService.selectById(busClass);
        }

        /**
         * Gets busClass by page.
         *
         * @return the busClass by page
         */
        @MethodAnnot
        public static Page<BusClass> getBusClassByPage(Page<BusClass> page){
            return busClassService.selectPage(page,
                new EntityWrapper<BusClass>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }

        /**
         * 查询该用户所有班级信息
         * @param user
         * @param page
         * @param paraRespInfo
         */
        @MethodAnnot
        public static void getClass(User user, Page<BusClass> page, RespInfo paraRespInfo){
                busClassService.getClass(user, page, paraRespInfo);
        }

        /**
         * 更新或操作班级信息
         * @param busClass
         * @param respInfo
         */
        @MethodAnnot
        public static void insertOrUpdateClass(BusClass busClass, RespInfo respInfo) {
                busClassService.insertOrUpdateClass(busClass, respInfo);
        }

        /**
         * 判断是否有班级存在
         * @param busCourse
         * @param respInfo
         */
        @MethodAnnot
        public static void hasClass(BusCourse busCourse, RespInfo respInfo) {
                busClassService.hasClass(busCourse, respInfo);
        }

        /**
         * 判断是否加入班级
         * @param user
         * @param busCourse
         * @param respInfo
         */
        @MethodAnnot
        public static void isJoinClass(User user, BusCourse busCourse, RespInfo respInfo) {
                busClassService.isJoinClass(user, busCourse, respInfo);
        }

        /**
         * 获取该课程的所有班级信息
         * @param busCourse
         * @param respInfo
         */
        @MethodAnnot
        public static void getClassesByCourse(BusCourse busCourse, RespInfo respInfo) {
                busClassService.getClassesByCourse(busCourse, respInfo);
        }

        /**
         * 获取该课程所有由该用户创建的班级信息
         * @param user
         * @param busCourse
         * @param respInfo
         */
        @MethodAnnot
        public static void getClassesByCourseCreatedByUser(User user, BusCourse busCourse, RespInfo respInfo) {
                busClassService.getClassesByCourseCreatedByUser(user, busCourse, respInfo);
        }

        /**
         * 加入班级
         * @param user
         * @param busCourse
         * @param busClass
         * @param respInfo
         */
        @MethodAnnot
        public static void joinClass(User user, BusCourse busCourse, BusClass busClass, RespInfo respInfo) {
                busClassService.joinClass(user, busCourse, busClass, respInfo);
        }

        /**
         * 获取该班级的课程用户信息
         * @param busClass
         * @param respInfo
         */
        @MethodAnnot
        public static void getBusCourseRecordByClassId(BusClass busClass, RespInfo respInfo) {
                busClassService.getBusCourseRecordByClassId(busClass, respInfo);
        }

        /**
         * 学生退出班级操作
         * @param busCourseRecord
         * @param respInfo
         */
        @MethodAnnot
        public static void quitClass(BusCourseRecord busCourseRecord, RespInfo respInfo) {
                busClassService.quitClass(busCourseRecord, respInfo);
        }

        /**
         * 关闭班级
         * @param busClass
         * @param respInfo
         */
        @MethodAnnot
        public static void deleteClass(BusClass busClass, RespInfo respInfo) {
                busClassService.deleteClass(busClass, respInfo);
        }

        /**
         * 获取班级唯一邀请码
         * @param user
         * @param respInfo
         */
        @MethodAnnot
        public static void getClassUUId(User user, RespInfo respInfo) {
                busClassService.getClassUUId(user, respInfo);
        }

        /**
         * 获取班级分页列表
         * @param page
         * @return
         */
        @MethodAnnot
        public static void getClassesWithName(Page<BusClass>  page, HttpServletRequest request,RespInfo respInfo) {
                 busClassService.getClassesWithName(page,request,respInfo);
        }
}