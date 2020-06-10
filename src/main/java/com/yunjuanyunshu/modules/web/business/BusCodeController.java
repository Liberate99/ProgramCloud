package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusCode;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusCodeService;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@Component
@ClassAnnot("business.busCode")
public class BusCodeController  {
        private static BusCodeService buscodeService;

        /**
         * 静态注入service
         *
         * @param buscodeService
         */
        @Autowired
        public BusCodeController (BusCodeService buscodeService){
            BusCodeController .buscodeService=buscodeService;
        }

        /**
         * Delete boolean.
         *
         * @param buscode the buscode
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusCode buscode){
                return buscodeService.deleteById(buscode);
        }

        @MethodAnnot
        public static void deleteCode(BusCode busCode, RespInfo respInfo) {
                buscodeService.deleteCode(busCode, respInfo);
        }


        @MethodAnnot
        public static void getCodeListByChapter(Page<BusCode> page, BusChapter busChapter, RespInfo respInfo){
                buscodeService.getCodeListByChapter(page,busChapter,respInfo);
        }

        @MethodAnnot
        public static void getCodeListToFront(BusChapter chapter, RespInfo respInfo) {
                buscodeService.getCodeListToFront(chapter,respInfo);
        }


        /**
         * Insert or update boolean.
         *
         * @param buscode the buscode
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusCode buscode) throws UnsupportedEncodingException {
                BusCode isExist = buscodeService.selectOne(new EntityWrapper<BusCode>().eq("chapter_id",buscode.getChapterId()));
                if (isExist != null) {
                        isExist.setContent(Base64Util.urlDecode(buscode.getContent()));
//                        isExist.setResult(Base64Util.urlDecode(buscode.getResult()));
                        return buscodeService.insertOrUpdate(isExist);
                } else {
                        buscode.setContent(Base64Util.urlDecode(buscode.getContent()));
//                        buscode.setResult(Base64Util.urlDecode(buscode.getResult()));
                        return buscodeService.insertOrUpdate(buscode);
                }
        }

        /**
         * Get buscode.
         *
         * @param buscode the buscode
         * @return the buscode
         */
        @MethodAnnot
        public static BusCode get(BusCode buscode){
            return buscodeService.selectById(buscode);
        }

        @MethodAnnot
        public static void insertOrUpdateCode(User user, BusChapter busChapter, BusCode busCode, RespInfo respInfo){
                buscodeService.insertOrUpdateCode(user,busChapter,busCode,respInfo);
        }

        @MethodAnnot
        public static void getByChapterId(BusCode buscode, RespInfo respInfo){

                buscodeService.getByChapterId(buscode,respInfo);
        }

        /**
         * Gets buscode by page.
         *
         * @return the buscode by page
         */
        @MethodAnnot
        public static Page<BusCode> getBusCodeByPage(Page<BusCode> page){
            return buscodeService.selectPage(page,
                new EntityWrapper<BusCode>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }


}