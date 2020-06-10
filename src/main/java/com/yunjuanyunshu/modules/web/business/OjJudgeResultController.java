package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjJudgeResult;
import com.yunjuanyunshu.modules.service.OjJudgeResultService;
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
@ClassAnnot("business.ojJudgeResult")
public class OjJudgeResultController  {
        private static OjJudgeResultService ojJudgeResultService;

        /**
         * 静态注入service
         *
         * @param ojJudgeResultService
         */
        @Autowired
        public OjJudgeResultController (OjJudgeResultService ojJudgeResultService){
            OjJudgeResultController .ojJudgeResultService=ojJudgeResultService;
        }

        /**
         * Delete boolean.
         *
         * @param ojJudgeResult the ojJudgeResult
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(OjJudgeResult ojJudgeResult){
                return ojJudgeResultService.deleteById(ojJudgeResult);
        }

        /**
         * Insert or update boolean.
         *
         * @param ojJudgeResult the ojJudgeResult
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(OjJudgeResult ojJudgeResult){
                return ojJudgeResultService.insertOrUpdate(ojJudgeResult);
        }

        /**
         * Get ojJudgeResult.
         *
         * @param ojJudgeResult the ojJudgeResult
         * @return the ojJudgeResult
         */
        @MethodAnnot
        public static OjJudgeResult get(OjJudgeResult ojJudgeResult){
                return ojJudgeResultService.selectById(ojJudgeResult);
        }

        /**
         * Gets ojJudgeResult by page.
         *
         * @return the ojJudgeResult by page
         */
        @MethodAnnot
        public static Page<OjJudgeResult> getOjJudgeResultByPage(Page<OjJudgeResult> page){
            return ojJudgeResultService.selectPage(page,
                new EntityWrapper<OjJudgeResult>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }
        }