package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.common.QuestionAndAnswerEnum;
import com.yunjuanyunshu.modules.common.RewardPointEnum;
import com.yunjuanyunshu.modules.mapper.BusQuestionDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.yunfd.common.util.StringUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 问题表 服务实现类
 * </p>
 *
 * @author msm
 * @since 2017-10-15
 */
@Service
public class BusQuestionServiceImpl extends ServiceImpl<BusQuestionDao, BusQuestion> implements BusQuestionService {
        @Autowired
        private UserService userService;
        @Autowired
        private BusChapterService busChapterService;
        @Autowired
        private BusQuestionDao busQuestionDao;
        @Autowired
        private RewardPointsService rewardPointsService;

        @Transactional(rollbackFor = Exception.class)
        @Override
        public void putQuestion(User user, BusQuestion busQuestion, RespInfo respInfo) {
                String questionWithCode = "1";
                String questionNoCode = "0";
                try {
                        user = userService.selectOne(new EntityWrapper<User>()
                                .eq("token", user.getToken()));
                        if(busQuestion.getCode()!=null){
                                busQuestion.setCode(Base64Util.urlDecode(busQuestion.getCode()));
                                busQuestion.setQuestionType(questionWithCode);
                        }else {
                                busQuestion.setQuestionType(questionNoCode);
                        }
                        BusChapter busChapter = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                                .eq("id", busQuestion.getChapter()));
                        busQuestion.setCourseId(busChapter.getCourseId());
                        Date nowDate = new Date();
                        busQuestion.setCreateDate(nowDate);
                        busQuestion.setUpdateDate(nowDate);
                        busQuestion.setCreateBy(user.getId());
                        super.insertOrUpdate(busQuestion);

                        //提问者积分减少
                        RewardPoints rewardPoints = new RewardPoints();
                        rewardPoints.setUserId(user.getId());
                        rewardPoints.setRewardPoints(busQuestion.getBonus());
                        rewardPoints.setEvent(RewardPointEnum.EVENT_ASK_QUESTION.getCode());
                        rewardPoints.setEventTime(new Date());
                        rewardPoints.setType(RewardPointEnum.SUBTRACT.getCode());
                        rewardPointsService.insert(rewardPoints);

                        respInfo.setMsg("提交问题成功");
                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                }catch(Exception ex) {
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                        respInfo.setMsg("提交问题失败");
                        respInfo.setError(ex.toString());
                }
        }

        @Override
        public void getCourseQuestionByPage(BusQuestion busQuestion, Page<BusQuestion> page, RespInfo respInfo) {
                try {
                        EntityWrapper<BusQuestion> ew = new EntityWrapper<>();
                        ew.eq("course_id", busQuestion.getCourseId());
                        respInfo.setValue(super.selectPage(page, ew.orderBy(page.getOrderByField(),
                                page.isAsc())));
                        respInfo.setMsg("查询问题成功");
                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                } catch(Exception ex) {
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                        respInfo.setMsg("查询问题失败");
                        respInfo.setError(ex.toString());
                }
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public void recommendQuestion(User user, BusQuestion busQuestion, RespInfo respInfo) {
                user = userService.selectOne(new EntityWrapper<User>().eq("token", user.getToken()));
                busQuestion = selectOne(new EntityWrapper<BusQuestion>().eq("id", busQuestion.getId()));
                String isRecommended = busQuestion.getRecommended();
                //未被推荐
                if (StringUtils.isBlank(isRecommended) || !QuestionAndAnswerEnum.QUESTION_IS_RECOMMENDED.equals(isRecommended)){
                        busQuestion.setRecommended(QuestionAndAnswerEnum.QUESTION_IS_RECOMMENDED.getCode());
                        busQuestion.setReferrer(user.getId());
                        final boolean update = updateById(busQuestion);

                         //提问者获得积分奖励
                        RewardPoints rewardPoints = new RewardPoints();
                        rewardPoints.setType(RewardPointEnum.ADD.getCode());
                        rewardPoints.setEvent(RewardPointEnum.EVENT_QUESTION_RECOMMENDED.getCode());
                        rewardPoints.setEventTime(new Date());
                        rewardPoints.setRewardPoints(RewardPointEnum.REWARD_QUESTION_RECOMMENDED.getAmount());
                        rewardPoints.setUserId(busQuestion.getCreateBy());
                        boolean insert = rewardPointsService.insert(rewardPoints);

                        if (update && insert){
                                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                                respInfo.setMsg("推荐问题成功");
                        }else {
                                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                                respInfo.setMsg("推荐问题失败");
                        }
                }else {
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                        respInfo.setMsg("该问题已经被推荐");
                }
        }

        @Override
        public void addBonus(User user, BusQuestion busQuestion, Integer increment, RespInfo respInfo) {
                user = userService.selectOne(new EntityWrapper<User>()
                        .eq("token", user.getToken()));
                if (user != null && user.getId().equals(busQuestion.getCreateBy())){
                        if (increment != null && increment > 0){
                                busQuestion.setBonus(busQuestion.getBonus() + increment);
                                boolean success = updateById(busQuestion);
                                if (success){
                                        respInfo.setMsg("更新成功");
                                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                                }
                        }else {
                                respInfo.setMsg("赏金数值不正确");
                                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                        }
                }else {
                        respInfo.setMsg("您没有权限修改问题赏金");
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                }
        }

        @Override
        public void searchQuestion(BusQuestion busQuestion, Page<BusQuestion> page, String key, RespInfo respInfo) {
                String courseId = busQuestion.getCourseId();
                respInfo.setValue(super.selectPage(page, new EntityWrapper<BusQuestion>().like("title", key).eq("course_id", courseId)));
                respInfo.setMsg("查询成功");
        }

        @Override
        public void delete(BusQuestion busQuestion, User user, RespInfo respInfo) {
                busQuestion = this.selectById(busQuestion);
                user = userService.selectOne(new EntityWrapper<User>().eq("token",user.getToken()));
                if (busQuestion != null && busQuestion.getCreateBy().equals(user.getId())){
                        this.deleteById(busQuestion);
                        respInfo.setMsg("删除成功");
                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                }else {
                        respInfo.setMsg("删除失败");
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                }
        }
}