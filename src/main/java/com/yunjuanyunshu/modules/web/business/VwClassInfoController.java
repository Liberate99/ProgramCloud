package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.entity.VwClassInfo;
import com.yunjuanyunshu.modules.service.VwClassInfoService;
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
@ClassAnnot("business.vwClassInfo")
public class VwClassInfoController  {
        private static VwClassInfoService vwClassInfoService;

        /**
         * 静态注入service
         *
         * @param vwClassInfoService
         */
        @Autowired
        public VwClassInfoController (VwClassInfoService vwClassInfoService){
            VwClassInfoController .vwClassInfoService=vwClassInfoService;
        }

        /**
         * Delete boolean.
         *
         * @param vwClassInfo the vwClassInfo
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(VwClassInfo vwClassInfo){
                return vwClassInfoService.deleteById(vwClassInfo);
        }

        /**
         * Insert or update boolean.
         *
         * @param vwClassInfo the vwClassInfo
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(VwClassInfo vwClassInfo){
                return vwClassInfoService.insertOrUpdate(vwClassInfo);
        }

        /**
         * Get vwClassInfo.
         *
         * @param vwClassInfo the vwClassInfo
         * @return the vwClassInfo
         */
        @MethodAnnot
        public static VwClassInfo get(VwClassInfo vwClassInfo){
                return vwClassInfoService.selectById(vwClassInfo);
        }

        /**
         * Gets vwClassInfo by page.
         *
         * @return the vwClassInfo by page
         */
        @MethodAnnot
        public static Page<VwClassInfo> getVwClassInfoByPage(Page<VwClassInfo> page){
            return vwClassInfoService.selectPage(page,
                new EntityWrapper<VwClassInfo>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }

        @MethodAnnot
        public static void getClassInfo(User user, Page<VwClassInfo> page, RespInfo respInfo) {
                vwClassInfoService.getClassInfo(user, page, respInfo);
        }
}