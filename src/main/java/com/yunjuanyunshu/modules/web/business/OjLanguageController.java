package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjLanguage;
import com.yunjuanyunshu.modules.service.OjLanguageService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
@Component
@ClassAnnot("business.ojLanguage")
public class OjLanguageController  {
        private static OjLanguageService ojLanguageService;

        /**
         * 静态注入service
         *
         * @param ojLanguageService
         */
        @Autowired
        public OjLanguageController (OjLanguageService ojLanguageService){
            OjLanguageController .ojLanguageService=ojLanguageService;
        }

        /**
         * Delete boolean.
         *
         * @param ojLanguage the ojLanguage
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(OjLanguage ojLanguage){
                return ojLanguageService.deleteById(ojLanguage);
        }

        /**
         * Insert or update boolean.
         *
         * @param ojLanguage the ojLanguage
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(OjLanguage ojLanguage){
                return ojLanguageService.insertOrUpdate(ojLanguage);
        }

        /**
         * Get ojLanguage.
         *
         * @param ojLanguage the ojLanguage
         * @return the ojLanguage
         */
        @MethodAnnot
        public static OjLanguage get(OjLanguage ojLanguage){
                return ojLanguageService.selectById(ojLanguage);
        }

        /**
         * Gets ojLanguage by page.
         *
         * @return the ojLanguage by page
         */
        @MethodAnnot
        public static Page<OjLanguage> getOjLanguageByPage(Page<OjLanguage> page){
            return ojLanguageService.selectPage(page,
                new EntityWrapper<OjLanguage>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }
        }