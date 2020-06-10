package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusKnowledgeCode;
import com.yunjuanyunshu.modules.service.BusKnowledgeCodeService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.RequestParameterCanNullAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
@Component
@ClassAnnot("business.busKnowledgeCode")
public class BusKnowledgeCodeController  {
        private static BusKnowledgeCodeService busknowledgecodeService;

        /**
         * 静态注入service
         *
         * @param busknowledgecodeService
         */
        @Autowired
        public BusKnowledgeCodeController (BusKnowledgeCodeService busknowledgecodeService){
            BusKnowledgeCodeController .busknowledgecodeService=busknowledgecodeService;
        }

        /**
         * Delete boolean.
         *
         * @param busknowledgecode the busknowledgecode
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusKnowledgeCode busknowledgecode){
                return busknowledgecodeService.deleteById(busknowledgecode);
        }

        /**
         * Insert or update boolean.
         *
         * @param busknowledgecode the busknowledgecode
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusKnowledgeCode busknowledgecode){
                return busknowledgecodeService.insertOrUpdate(busknowledgecode);
        }

        /**
         * Get busknowledgecode.
         *
         * @param busknowledgecode the busknowledgecode
         * @return the busknowledgecode
         */
        @MethodAnnot
        public static BusKnowledgeCode get(BusKnowledgeCode busknowledgecode){
                return busknowledgecodeService.selectById(busknowledgecode);
        }

        /**
         * Gets busknowledgecode by page.
         *
         * @return the busknowledgecode by page
         */
        @MethodAnnot
        public static Page<BusKnowledgeCode> getBusKnowledgeCodeByPage(Page<BusKnowledgeCode> page){
            return busknowledgecodeService.selectPage(page,
                new EntityWrapper<BusKnowledgeCode>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }


        /**
         * Gets busknowledgecode by page.
         *
         * @return the busknowledgecode by page
         */
        @MethodAnnot
        public static Page<BusKnowledgeCode> getBusKnowledgeCodeByChapter(Page<BusKnowledgeCode> page, String chapterId){
                return busknowledgecodeService.selectPage(page,
                        new EntityWrapper<BusKnowledgeCode>().eq("chapter_id",chapterId).orderBy(page.getOrderByField(),
                                page.isAsc()));
        }
        }

