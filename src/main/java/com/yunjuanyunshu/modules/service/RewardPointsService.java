package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.RewardPoints;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 积分明细表 服务类
 * </p>
 *
 * @author carl
 * @since 2018-02-05
 */
public interface RewardPointsService extends IService<RewardPoints> {


    /**
     * 获取积分余额
     * @param rewardPoints
     * @param respInfo
     * @return
     */
    void getBalance(RewardPoints rewardPoints, RespInfo respInfo);
}
