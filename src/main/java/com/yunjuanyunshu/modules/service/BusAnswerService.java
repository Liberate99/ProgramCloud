package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.*;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 回答表 服务类
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
public interface BusAnswerService extends IService<BusAnswer> {
        void getQuestionAnswerByPage(BusAnswer busAnswer,BusCourse busCourse, Page<BusAnswer> page, RespInfo respInfo);
        void answerQuestion(BusAnswer busAnswer, RespInfo respInfo);
        void updateSupportNum(BusAnswer busAnswer, RespInfo respInfo);
        void acceptAnswer(User user, BusQuestion busQuestion, BusAnswer busAnswer, RespInfo respInfo);

        void approveAnswer(User user, BusAnswer busAnswer, AnswerLike busAnswerLike, RespInfo respInfo);

        void recommendAnswer(User user, BusAnswer busAnswer, RespInfo respInfo);

        /**
         * 被采纳的回答
         * @param busAnswer

         * @param respInfo
         */
        void getAcceptedAnswer(BusAnswer busAnswer, RespInfo respInfo);

        /**
         * 被推荐的回答
         * @param busAnswer
         * @param respInfo
         */
        void getRecommendedAnswer(BusAnswer busAnswer, RespInfo respInfo);
}