package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.common.AnswerLikeEnum;
import com.yunjuanyunshu.modules.common.QuestionAndAnswerEnum;
import com.yunjuanyunshu.modules.common.RewardPointEnum;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.BusAnswerDao;
import com.yunjuanyunshu.modules.mapper.BusChapterDao;
import com.yunjuanyunshu.modules.service.*;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.yunfd.common.util.RespInfoUtil;
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
public class EvaluateServiceImpl implements EvaluateService {

    @Autowired
    BusChapterDao chapterDao;
    @Autowired
    private UserService userService;
    @Autowired
    private BusCourseService busCourseService;
    @Autowired
    private RewardPointsService rewardPointsService;
    @Autowired
    private AnswerLikeService answerLikeService;

    @Override
    public void getEvaluateInfoByClassId(BusClass busClass, RespInfo respInfo) {
        List<EvaluateVO> evaluateVOS = chapterDao.getEvaluateByClassId(busClass.getId());
        List<String> chapterTitles = Lists.newArrayList();
        List<Integer> chapterPassTimes = Lists.newArrayList();
        List<Integer> chapterUnPassTimes = Lists.newArrayList();
        for(EvaluateVO evaluateVO:evaluateVOS){
            chapterTitles.add(evaluateVO.getTitle());
            chapterPassTimes.add(evaluateVO.getPassTime());
            chapterUnPassTimes.add(evaluateVO.getUnPasstime());
        }
        RespInfoUtil.success(evaluateVOS);
        //

    }
}