package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusAnswer;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.BusQuestion;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusQuestionService;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.common.util.RespInfoUtil;
import com.yunjuanyunshu.yunfd.common.util.UserUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 问题表 前端控制器
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
@Component
@ClassAnnot("business.busQuestion")
public class BusQuestionController {
        private static BusQuestionService busquestionService;

        /**
         * 静态注入service
         *
         * @param busquestionService
         */
        @Autowired
        public BusQuestionController(BusQuestionService busquestionService){
            BusQuestionController.busquestionService=busquestionService;
        }

        /**
         * Delete boolean.
         *
         * @param busquestion the busquestion
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusQuestion busquestion){
                return busquestionService.deleteById(busquestion);
        }

        @MethodAnnot
        public static void deleteQuestion(BusQuestion busQuestion,User user,RespInfo respInfo){
                busquestionService.delete(busQuestion,user,respInfo);
        }

        /**
         * get Question
         *
         * @param busQuestion
         * @param respInfo
         */
        @MethodAnnot
        public static void get(BusQuestion busQuestion, RespInfo respInfo){
                busQuestion = busquestionService.selectById(busQuestion);
                String busMsg = "不存在此id";
                ServiceErrorCodeEnum seEnum = ServiceErrorCodeEnum.ERROR;
                if(busQuestion != null) {
                        busMsg = "查询成功";
                        seEnum = ServiceErrorCodeEnum.SUCCESS;
                }
                RespInfoUtil.businessError(busQuestion, busMsg,
                        seEnum, respInfo);
        }

        /**
         * Gets busquestion by page.
         *
         * @return the busquestion by page
         */
        @MethodAnnot
        public static Page<BusQuestion> getBusQuestionByPage(Page<BusQuestion> page,BusQuestion busQuestion){
                EntityWrapper<BusQuestion> ew=new EntityWrapper<>(busQuestion);
                ew.orderBy(page.getOrderByField(), page.isAsc());
            return busquestionService.selectPage(page, ew);
        }
        /**
         * 发布问题
         * @param user
         * @param busQuestion
         * @param respInfo
         */
        @MethodAnnot
        public static void putQuestion(User user, BusQuestion busQuestion, RespInfo respInfo) {
                busquestionService.putQuestion(user, busQuestion, respInfo);
        }

        /**
         * 分页获取问题列表
         * 可根据需要更改排序方式，甚至正序倒序
         * @param busQuestion
         * @param page
         * @param respInfo
         */
        @MethodAnnot
        public static void getCourseQuestionByPage(BusQuestion busQuestion, Page<BusQuestion> page, RespInfo respInfo) {
                busquestionService.getCourseQuestionByPage(busQuestion, page, respInfo);
        }

        /**
         * 老师推荐问题
         * @param user 推荐人
         * @param busQuestion 问题
         * @param respInfo 返回值
         */
        @MethodAnnot
        public static void recommendQuestion(User user, BusQuestion busQuestion, RespInfo respInfo){
                busquestionService.recommendQuestion(user,busQuestion,respInfo);
        }

        /**
         * 增加问题赏金
         * @param user
         * @param busQuestion
         * @param respInfo
         */
        @MethodAnnot
        public static void addBonus(User user, BusQuestion busQuestion,Integer increment, RespInfo respInfo){
                busquestionService.addBonus(user,busQuestion,increment,respInfo);
        }

        /**
         * 搜索问题
         * @param key 关键字
         * @param respInfo 返回值
         */
        @MethodAnnot
        public static void searchQuestion(BusQuestion busQuestion, Page<BusQuestion> page, String key, RespInfo respInfo){
                busquestionService.searchQuestion(busQuestion,page,key,respInfo);
        }

}