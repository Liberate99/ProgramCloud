package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.BusCourseRecord;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusCourseRecordService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@Component
@ClassAnnot("business.busCourseRecord")
public class BusCourseRecordController  {
        private static BusCourseRecordService buscourserecordService;

        /**
         * 静态注入service
         *
         * @param buscourserecordService
         */
        @Autowired
        public BusCourseRecordController (BusCourseRecordService buscourserecordService){
            BusCourseRecordController .buscourserecordService=buscourserecordService;
        }

        /**
         * Delete boolean.
         *
         * @param buscourserecord the buscourserecord
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusCourseRecord buscourserecord){
                return buscourserecordService.deleteById(buscourserecord);
        }

        /**
         * Insert or update boolean.
         *
         * @param buscourserecord the buscourserecord
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusCourseRecord buscourserecord){
                return buscourserecordService.insertOrUpdate(buscourserecord);
        }

        /**
         * Get buscourserecord.
         *
         * @param buscourserecord the buscourserecord
         * @return the buscourserecord
         */
        @MethodAnnot
        public static BusCourseRecord get(BusCourseRecord buscourserecord){
                return buscourserecordService.selectById(buscourserecord);
        }

        /**
         * Gets buscourserecord by page.
         *
         * @return the buscourserecord by page
         */
        @MethodAnnot
        public static Page<BusCourseRecord> getUserByPage(Page<BusCourseRecord> page){
            return buscourserecordService.selectPage(page,
                new EntityWrapper<BusCourseRecord>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }

        /**
         * 根据用户关注课程表，取用户最近关注的四门课程返回
         * @param user
         * @param num
         * @param respInfo
         */
        @MethodAnnot
        public static void getLatestCourseRecordByUser(User user, int num, RespInfo respInfo){
                buscourserecordService.getLatestCourseRecordByUser(user, num, respInfo);
        }

        @MethodAnnot
        public static void getLastestChapter(User user, BusCourse busCourse, RespInfo respInfo) {
                buscourserecordService.getLastestChapter(user, busCourse, respInfo);
        }

        @MethodAnnot
        public static void beginStudy(User user, BusCourse busCourse, RespInfo respInfo) {
                buscourserecordService.beginStudy(user, busCourse, respInfo);
        }

        @MethodAnnot
        public static void setLastestChapter(User user, BusChapter busChapter, RespInfo respInfo) {
                buscourserecordService.setLastestChapter(user, busChapter ,respInfo);
        }
}