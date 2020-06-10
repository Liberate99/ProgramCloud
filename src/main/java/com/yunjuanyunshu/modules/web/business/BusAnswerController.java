package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.service.BusAnswerService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 回答表 前端控制器
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
@Component
@ClassAnnot("business.busAnswer")
public class BusAnswerController  {
        private static BusAnswerService busanswerService;

        /**
         * 静态注入service
         *
         * @param busanswerService
         */
        @Autowired
        public BusAnswerController (BusAnswerService busanswerService){
            BusAnswerController .busanswerService=busanswerService;
        }

        /**
         * Delete boolean.
         *
         * @param busanswer the busanswer
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusAnswer busanswer){
                return busanswerService.deleteById(busanswer);
        }

        /**
         * Insert or update boolean.
         *
         * @param busanswer the busanswer
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusAnswer busanswer){
                return busanswerService.insertOrUpdate(busanswer);
        }

        /**
         * Get busanswer.
         *
         * @param busanswer the busanswer
         * @return the busanswer
         */
        @MethodAnnot
        public static BusAnswer get(BusAnswer busanswer){
                return busanswerService.selectById(busanswer);
        }

        /**
         * Gets busanswer by page.
         *
         * @return the busanswer by page
         */
        @MethodAnnot
        public static Page<BusAnswer> getBusAnswerByPage(Page<BusAnswer> page){
            return busanswerService.selectPage(page,
                new EntityWrapper<BusAnswer>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }

        /**
         * 回答相应问题
         *
         * @param busAnswer
         * @param respInfo
         */
        @MethodAnnot
        public static void answerQuestion(BusAnswer busAnswer, RespInfo respInfo) {
                busanswerService.answerQuestion(busAnswer, respInfo);
        }

        /**
         * 分页获取回答列表
         * 可根据需要更改排序方式，甚至正序倒序
         * @param busAnswer
         * @param page
         * @param respInfo
         */
        @MethodAnnot
        public static void getQuestionAnswerByPage(BusAnswer busAnswer,BusCourse busCourse, Page<BusAnswer> page, RespInfo respInfo) {
                busanswerService.getQuestionAnswerByPage(busAnswer,busCourse, page, respInfo);
        }

        /**
         * 获取被采纳的回答
         * @param busAnswer
         * @param respInfo
         */
        @MethodAnnot
        public static void getAcceptedAnswer(BusAnswer busAnswer,RespInfo respInfo){
                busanswerService.getAcceptedAnswer(busAnswer,respInfo);
        }


        /**
         * 获取被推荐的回答
         * @param busAnswer
         * @param respInfo
         */
        @MethodAnnot
        public static void getRecommendedAnswer(BusAnswer busAnswer,RespInfo respInfo){
                busanswerService.getRecommendedAnswer(busAnswer,respInfo);
        }

        /**
         * 采纳回答
         */
        @MethodAnnot
        public static void acceptAnswer(User user, BusQuestion busQuestion, BusAnswer busAnswer, RespInfo respInfo){
                busanswerService.acceptAnswer(user,busQuestion,busAnswer,respInfo);
        }

        /**
         * 老师推荐回答
         */
        @MethodAnnot
        public static void recommendAnswer(User user, BusAnswer busAnswer, RespInfo respInfo){
                busanswerService.recommendAnswer(user,busAnswer,respInfo);
        }

        /**
         * 赞同回答
         */
        @MethodAnnot
        public static void approveAnswer(User user, BusAnswer busAnswer, AnswerLike busAnswerLike, RespInfo respInfo){
                busanswerService.approveAnswer(user,busAnswer,busAnswerLike,respInfo);
        }
}