package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjProblemTagRelation;
import com.yunjuanyunshu.modules.service.OjProblemTagRelationService;
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
@ClassAnnot("business.ojProblemTagRelation")
public class OjProblemTagRelationController  {
        private static OjProblemTagRelationService ojProblemTagRelationService;

        /**
         * 静态注入service
         *
         * @param ojProblemTagRelationService
         */
        @Autowired
        public OjProblemTagRelationController (OjProblemTagRelationService ojProblemTagRelationService){
            OjProblemTagRelationController .ojProblemTagRelationService=ojProblemTagRelationService;
        }

        /**
         * Delete boolean.
         *
         * @param ojProblemTagRelation the ojProblemTagRelation
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(OjProblemTagRelation ojProblemTagRelation){
                return ojProblemTagRelationService.deleteById(ojProblemTagRelation);
        }

        /**
         * Insert or update boolean.
         *
         * @param ojProblemTagRelation the ojProblemTagRelation
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(OjProblemTagRelation ojProblemTagRelation){
                return ojProblemTagRelationService.insertOrUpdate(ojProblemTagRelation);
        }

        /**
         * Get ojProblemTagRelation.
         *
         * @param ojProblemTagRelation the ojProblemTagRelation
         * @return the ojProblemTagRelation
         */
        @MethodAnnot
        public static OjProblemTagRelation get(OjProblemTagRelation ojProblemTagRelation){
                return ojProblemTagRelationService.selectById(ojProblemTagRelation);
        }

        /**
         * Gets ojProblemTagRelation by page.
         *
         * @return the ojProblemTagRelation by page
         */
        @MethodAnnot
        public static Page<OjProblemTagRelation> getOjProblemTagRelationByPage(Page<OjProblemTagRelation> page){
            return ojProblemTagRelationService.selectPage(page,
                new EntityWrapper<OjProblemTagRelation>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }
        }