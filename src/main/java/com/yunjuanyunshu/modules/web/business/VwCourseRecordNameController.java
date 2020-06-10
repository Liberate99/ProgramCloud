package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusClass;
import com.yunjuanyunshu.modules.entity.VwCourseRecordName;
import com.yunjuanyunshu.modules.service.VwCourseRecordNameService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author carl
 * @since 2017-11-02
 */
@Component
@ClassAnnot("business.vwCourseRecordName")
public class VwCourseRecordNameController  {
        private static VwCourseRecordNameService vwCourseRecordNameService;

        /**
         * 静态注入service
         *
         * @param vwCourseRecordNameService
         */
        @Autowired
        public VwCourseRecordNameController (VwCourseRecordNameService vwCourseRecordNameService){
            VwCourseRecordNameController .vwCourseRecordNameService=vwCourseRecordNameService;
        }

        /**
         * Delete boolean.
         *
         * @param vwCourseRecordName the vwCourseRecordName
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(VwCourseRecordName vwCourseRecordName){
                return vwCourseRecordNameService.deleteById(vwCourseRecordName);
        }

        /**
         * Insert or update boolean.
         *
         * @param vwCourseRecordName the vwCourseRecordName
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(VwCourseRecordName vwCourseRecordName){
                return vwCourseRecordNameService.insertOrUpdate(vwCourseRecordName);
        }

        /**
         * Get vwCourseRecordName.
         *
         * @param vwCourseRecordName the vwCourseRecordName
         * @return the vwCourseRecordName
         */
        @MethodAnnot
        public static VwCourseRecordName get(VwCourseRecordName vwCourseRecordName){
                return vwCourseRecordNameService.selectById(vwCourseRecordName);
        }

        /**
         * Gets vwCourseRecordName by page.
         *
         * @return the vwCourseRecordName by page
         */
        @MethodAnnot
        public static Page<VwCourseRecordName> getVwCourseRecordNameByPage(Page<VwCourseRecordName> page){
            return vwCourseRecordNameService.selectPage(page,
                new EntityWrapper<VwCourseRecordName>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }

        @MethodAnnot
        public static void getCourseRecordName(Page<VwCourseRecordName> page, BusClass busClass, RespInfo respInfo){
                vwCourseRecordNameService.getCourseRecordName(page, busClass, respInfo);
        }
}