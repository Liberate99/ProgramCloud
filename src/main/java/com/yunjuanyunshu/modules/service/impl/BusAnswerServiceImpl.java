package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.common.AnswerLikeEnum;
import com.yunjuanyunshu.modules.common.QuestionAndAnswerEnum;
import com.yunjuanyunshu.modules.common.RewardPointEnum;
import com.yunjuanyunshu.modules.mapper.BusAnswerDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回答表 服务实现类
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
@Service
public class BusAnswerServiceImpl extends ServiceImpl<BusAnswerDao, BusAnswer> implements BusAnswerService {

    @Autowired
    BusQuestionService busQuestionService;
    @Autowired
    private UserService userService;
    @Autowired
    private BusCourseService busCourseService;
    @Autowired
    private RewardPointsService rewardPointsService;
    @Autowired
    private AnswerLikeService answerLikeService;

    @Override
    public void answerQuestion(BusAnswer busAnswer, RespInfo respInfo) {
        try {
            BusQuestion busQuestion = busQuestionService.selectOne(new EntityWrapper<BusQuestion>()
                .eq("id", busAnswer.getQuestionId()));

            busQuestion.setAnswer(busQuestion.getAnswer() + 1);
            busQuestionService.updateById(busQuestion);
            if(busAnswer.getCode()!=null){
                busAnswer.setCode(Base64Util.urlDecode(busAnswer.getCode()));
            }
            busAnswer.setCreateDate(new Date());
            busAnswer.setSupportNum(0);
            super.insert(busAnswer);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("提交回答成功");

        } catch(Exception ex) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("提交回答失败");
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void updateSupportNum(BusAnswer busAnswer, RespInfo respInfo) {
        try {
            busAnswer = super.selectOne(new EntityWrapper<BusAnswer>()
                .eq("id", busAnswer.getId()));
            int num = busAnswer.getSupportNum() + 1;
            busAnswer.setSupportNum(num);
            super.updateById(busAnswer);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("支持成功");

        } catch(Exception ex) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("支持失败");
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void getQuestionAnswerByPage(BusAnswer busAnswer,BusCourse busCourse, Page<BusAnswer> page, RespInfo respInfo) {
        try {
            Map result = Maps.newHashMap();

            Page<BusAnswer> answerPage = super.selectPage(page, new EntityWrapper<BusAnswer>()
                    .ne("create_by",busCourse.getCreateBy())
                    .eq("question_id", busAnswer.getQuestionId())
                    .ne("accepted",QuestionAndAnswerEnum.ANSWER_IS_ACCEPTED.getCode())
                    .ne("recommended",QuestionAndAnswerEnum.ANSWER_IS_RECOMMENDED.getCode())
                    .orderBy(page.getOrderByField(), page.isAsc()));
            List<BusAnswer> teacherAnswer = selectList(new EntityWrapper<BusAnswer>()
                    .eq("create_by",busCourse.getCreateBy())
                    .eq("question_id",busAnswer.getQuestionId()));
            List<BusAnswer> acceptedAnswer = super.selectList(new EntityWrapper<BusAnswer>()
                    .eq("question_id",busAnswer.getQuestionId())
                    .eq("accepted",QuestionAndAnswerEnum.ANSWER_IS_ACCEPTED.getCode()));
            List<BusAnswer> recommendedAnswer = super.selectList(new EntityWrapper<BusAnswer>()
                    .eq("question_id",busAnswer.getQuestionId())
                    .eq("recommended",QuestionAndAnswerEnum.ANSWER_IS_RECOMMENDED.getCode()));
            result.put("acceptedAnswer",acceptedAnswer);
            result.put("recommendedAnswer",recommendedAnswer);
            result.put("answerPage",answerPage);
            result.put("teacherAnswer",teacherAnswer);
            respInfo.setValue(result);
            respInfo.setMsg("答案获取成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch(Exception ex) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("答案获取失败");
            respInfo.setError(ex.toString());
        }
    }


    /**
     * 采纳回答
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptAnswer(User user, BusQuestion busQuestion, BusAnswer busAnswer, RespInfo respInfo) {
        try {
            busQuestion = busQuestionService.selectById(busQuestion);
            //查询该问题是否已经有最佳答案
            List<BusAnswer> best = this.selectList(new EntityWrapper<BusAnswer>()
                    .eq("question_id",busQuestion.getId())
                    .eq("accepted",QuestionAndAnswerEnum.ANSWER_IS_ACCEPTED.getCode()));
            //1判断用户是否是问题提问者
            user = userService.selectOne(new EntityWrapper<User>().eq("token",user.getToken()));
            if (user.getId().equals(busQuestion.getCreateBy())){

                if (best.size() == 0){
                    //2回答被采纳
                    busAnswer = this.selectById(busAnswer);
                    if (busAnswer != null){
                        busAnswer.setAccepted(QuestionAndAnswerEnum.ANSWER_IS_ACCEPTED.getCode());
                        super.updateById(busAnswer);
                    }
                    //3回答者积分增加
                    RewardPoints rewardPoints = new RewardPoints();
                    rewardPoints.setType(RewardPointEnum.ADD.getCode());
                    rewardPoints.setEvent(RewardPointEnum.EVENT_ANSWER_ACCEPTED.getCode());
                    rewardPoints.setEventTime(new Date());
                    rewardPoints.setUserId(busAnswer.getCreateBy());
                    rewardPoints.setRewardPoints(busQuestion.getBonus());
                    rewardPointsService.insert(rewardPoints);

                    //4问题状态改为已解答
                    busQuestion.setStatus(QuestionAndAnswerEnum.QUESTION_STATUS_SOLVED.getCode());
                    busQuestionService.updateById(busQuestion);

                    respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                    respInfo.setMsg("采纳成功");
                }else {
                    respInfo.setMsg("该问题已经有最佳回答");
                    respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                }
            }else {
                respInfo.setMsg("您没有采纳该回答的权限");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            }
        }catch (Exception e){
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("采纳失败");
            respInfo.setError(e.toString());
        }
    }

    /**
     * 赞同回答
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approveAnswer(User user, BusAnswer busAnswer,AnswerLike busAnswerLike, RespInfo respInfo) {
        user = userService.selectOne(new EntityWrapper<User>().eq("token",user.getToken()));
        busAnswer = super.selectById(busAnswer);
        String status = busAnswerLike.getStatus();
        AnswerLike selectOne = answerLikeService.selectOne(new EntityWrapper<AnswerLike>().eq("user_id", user.getId()));

        if (selectOne !=  null){
            if (!selectOne.getStatus().equals(status)){

                selectOne.setStatus(status);
                selectOne.setUpdateDate(new Date());
                answerLikeService.updateById(selectOne);
                //点赞
                if (status.equals(AnswerLikeEnum.STATUS_LIKE.getCode())) {
                    //点赞数量加1
                    busAnswer.setSupportNum(busAnswer.getSupportNum() + 1);
                    super.updateById(busAnswer);
                }else if (status.equals(AnswerLikeEnum.STATUS_NOT_LIKE.getCode())){
                    //点赞数量减一
                    busAnswer.setSupportNum(busAnswer.getSupportNum() - 1);
                    super.updateById(busAnswer);
                }
                respInfo.setMsg("操作成功");
            }else {
                respInfo.setError("点赞码错误");
            }
        }else {
            //回答点赞表中插入一条数据
            AnswerLike answerLike = new AnswerLike();
            answerLike.setAnswerId(busAnswer.getId());
            answerLike.setStatus(status);
            answerLike.setCreateDate(new Date());
            answerLike.setUpdateDate(new Date());
            answerLike.setUserId(user.getId());
            answerLikeService.insert(answerLike);
            busAnswer.setSupportNum(busAnswer.getSupportNum() + 1);
            super.updateById(busAnswer);
            respInfo.setMsg("点赞成功");
        }
    }

    /**
     * 推荐回答
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void recommendAnswer(User user, BusAnswer busAnswer, RespInfo respInfo) {
        user = userService.selectOne(new EntityWrapper<User>().eq("token", user.getToken()));
        busAnswer = selectOne(new EntityWrapper<BusAnswer>().eq("id", busAnswer.getId()));
        String isApproved = busAnswer.getRecommended();
        if (isApproved == null || !QuestionAndAnswerEnum.ANSWER_IS_RECOMMENDED.getCode().equals(isApproved)){
            //1 回答设置为被推荐
            busAnswer.setRecommended(QuestionAndAnswerEnum.ANSWER_IS_RECOMMENDED.getCode());
            busAnswer.setReferrer(user.getId());
            boolean updated = updateById(busAnswer);

            //2 回答者获得积分奖励
            RewardPoints rewardPoints = new RewardPoints();
            rewardPoints.setEvent(RewardPointEnum.EVENT_ANSWER_RECOMMENDED.getCode());
            rewardPoints.setType(RewardPointEnum.ADD.getCode());
            rewardPoints.setEventTime(new Date());
            rewardPoints.setRewardPoints(RewardPointEnum.REWARD_ANSWER_RECOMMENDED.getAmount());
            rewardPoints.setUserId(busAnswer.getCreateBy());
            boolean insert = rewardPointsService.insert(rewardPoints);
            if (updated && insert){
                respInfo.setMsg("推荐回答成功");
                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            }
        }else {
            respInfo.setMsg("该回答已被推荐");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }

    }

    @Override
    public void getAcceptedAnswer(BusAnswer busAnswer, RespInfo respInfo) {
        try {
            List<BusAnswer> acceptedAnswer = super.selectList(new EntityWrapper<BusAnswer>()
                    .eq("question_id",busAnswer.getQuestionId())
                    .eq("accepted",QuestionAndAnswerEnum.ANSWER_IS_ACCEPTED.getCode()));
            respInfo.setMsg("查询成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setValue(acceptedAnswer);
        }catch (Exception e){
            respInfo.setMsg("查询失败");
            respInfo.setError(e.toString());
        }
    }

    @Override
    public void getRecommendedAnswer(BusAnswer busAnswer,RespInfo respInfo){
        try {
            List<BusAnswer> recommendedAnswer = super.selectList(new EntityWrapper<BusAnswer>()
                    .eq("question_id",busAnswer.getQuestionId())
                    .eq("recommended",QuestionAndAnswerEnum.ANSWER_IS_RECOMMENDED.getCode()));
            respInfo.setMsg("查询成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setValue(recommendedAnswer);
        }catch (Exception e){
            respInfo.setMsg("查询失败");
            respInfo.setError(e.toString());
        }
    }
}