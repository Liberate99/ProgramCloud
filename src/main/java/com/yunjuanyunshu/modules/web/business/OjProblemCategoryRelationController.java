package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjProblemCategoryRelation;
import com.yunjuanyunshu.modules.service.OjProblemCategoryRelationService;
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
@ClassAnnot("business.ojProblemCategoryRelation")
public class OjProblemCategoryRelationController  {
        private static OjProblemCategoryRelationService ojProblemCategoryRelationService;

        /**
         * 静态注入service
         *
         * @param ojProblemCategoryRelationService
         */
        @Autowired
        public OjProblemCategoryRelationController (OjProblemCategoryRelationService ojProblemCategoryRelationService){
            OjProblemCategoryRelationController .ojProblemCategoryRelationService=ojProblemCategoryRelationService;
        }

        /**
         * Delete boolean.
         *
         * @param ojProblemCategoryRelation the ojProblemCategoryRelation
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(OjProblemCategoryRelation ojProblemCategoryRelation){
                return ojProblemCategoryRelationService.deleteById(ojProblemCategoryRelation);
        }

        /**
         * Insert or update boolean.
         *
         * @param ojProblemCategoryRelation the ojProblemCategoryRelation
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(OjProblemCategoryRelation ojProblemCategoryRelation){
                return ojProblemCategoryRelationService.insertOrUpdate(ojProblemCategoryRelation);
        }

        /**
         * Get ojProblemCategoryRelation.
         *
         * @param ojProblemCategoryRelation the ojProblemCategoryRelation
         * @return the ojProblemCategoryRelation
         */
        @MethodAnnot
        public static OjProblemCategoryRelation get(OjProblemCategoryRelation ojProblemCategoryRelation){
                return ojProblemCategoryRelationService.selectById(ojProblemCategoryRelation);
        }

        /**
         * Gets ojProblemCategoryRelation by page.
         *
         * @return the ojProblemCategoryRelation by page
         */
        @MethodAnnot
        public static Page<OjProblemCategoryRelation> getOjProblemCategoryRelationByPage(Page<OjProblemCategoryRelation> page){
            return ojProblemCategoryRelationService.selectPage(page,
                new EntityWrapper<OjProblemCategoryRelation>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }
        }