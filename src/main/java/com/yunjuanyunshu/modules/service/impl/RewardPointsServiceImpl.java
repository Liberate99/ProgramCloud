package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunjuanyunshu.modules.entity.RewardPoints;
import com.yunjuanyunshu.modules.common.RewardPointEnum;
import com.yunjuanyunshu.modules.mapper.RewardPointsDao;
import com.yunjuanyunshu.modules.service.RewardPointsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分明细表 服务实现类
 * </p>
 *
 * @author carl
 * @since 2018-02-05
 */
@Service
public class RewardPointsServiceImpl extends ServiceImpl<RewardPointsDao, RewardPoints> implements RewardPointsService {


    @Override
    public void getBalance(RewardPoints rewardPoints, RespInfo respInfo) {
        Integer balance = 0;
        Integer in = super.selectCount(new EntityWrapper<RewardPoints>().setSqlSelect("sum(reward_points)").eq("type", RewardPointEnum.ADD.getCode()).eq("user_id",rewardPoints.getUserId()));
        Integer out = super.selectCount(new EntityWrapper<RewardPoints>().setSqlSelect("sum(reward_points)").eq("type", RewardPointEnum.SUBTRACT.getCode()).eq("user_id",rewardPoints.getUserId()));
        if (in > 0 ){
            balance = in - out;
        }
        respInfo.setValue(balance);
        respInfo.setMsg("获取成功");
    }
}