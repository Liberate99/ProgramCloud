package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.RewardPoints;
import com.yunjuanyunshu.modules.service.RewardPointsService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 积分明细表 前端控制器
 * </p>
 *
 * @author carl
 * @since 2018-02-05
 */
@Component
@ClassAnnot("business.rewardPoints")
public class RewardPointsController  {
        private static RewardPointsService rewardPointsService;

        /**
         * 静态注入service
         *
         * @param rewardPointsService
         */
        @Autowired
        public RewardPointsController (RewardPointsService rewardPointsService){
            RewardPointsController .rewardPointsService=rewardPointsService;
        }

        /**
         * Delete boolean.
         *
         * @param rewardPoints the rewardPoints
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(RewardPoints rewardPoints){
                return rewardPointsService.deleteById(rewardPoints);
        }

        /**
         * Insert or update boolean.
         *
         * @param rewardPoints the rewardPoints
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(RewardPoints rewardPoints){
                return rewardPointsService.insertOrUpdate(rewardPoints);
        }

        /**
         * Get rewardPoints.
         *
         * @param rewardPoints the rewardPoints
         * @return the rewardPoints
         */
        @MethodAnnot
        public static RewardPoints get(RewardPoints rewardPoints){
                return rewardPointsService.selectById(rewardPoints);
        }

        /**
         * Gets rewardPoints by page.
         *
         * @return the rewardPoints by page
         */
        @MethodAnnot
        public static Page<RewardPoints> getRewardPointsByPage(Page<RewardPoints> page){
            return rewardPointsService.selectPage(page,
                new EntityWrapper<RewardPoints>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }

        /**
         * 查询积分余额
         */
        @MethodAnnot
        public static void checkBalance(RewardPoints rewardPoints, RespInfo respInfo){
                rewardPointsService.getBalance(rewardPoints,respInfo);
        }
}