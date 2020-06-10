package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.AnswerLike;
import com.yunjuanyunshu.modules.service.AnswerLikeService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 回答点赞表 前端控制器
 * </p>
 *
 * @author carl
 * @since 2018-02-05
 */
@Component
@ClassAnnot("business.answerLike")
public class AnswerLikeController  {
        private static AnswerLikeService answerLikeService;

        /**
         * 静态注入service
         *
         * @param answerLikeService
         */
        @Autowired
        public AnswerLikeController (AnswerLikeService answerLikeService){
            AnswerLikeController .answerLikeService=answerLikeService;
        }

        /**
         * Delete boolean.
         *
         * @param answerLike the answerLike
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(AnswerLike answerLike){
                return answerLikeService.deleteById(answerLike);
        }

        /**
         * Insert or update boolean.
         *
         * @param answerLike the answerLike
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(AnswerLike answerLike){
                return answerLikeService.insertOrUpdate(answerLike);
        }

        /**
         * Get answerLike.
         *
         * @param answerLike the answerLike
         * @return the answerLike
         */
        @MethodAnnot
        public static AnswerLike get(AnswerLike answerLike){
                return answerLikeService.selectById(answerLike);
        }

        /**
         * Gets answerLike by page.
         *
         * @return the answerLike by page
         */
        @MethodAnnot
        public static Page<AnswerLike> getAnswerLikeByPage(Page<AnswerLike> page){
            return answerLikeService.selectPage(page,
                new EntityWrapper<AnswerLike>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }
        }