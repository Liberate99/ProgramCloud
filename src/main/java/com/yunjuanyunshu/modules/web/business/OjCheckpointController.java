package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjCheckpoint;
import com.yunjuanyunshu.modules.service.OjCheckpointService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
@Component
@ClassAnnot("business.ojCheckpoint")
public class OjCheckpointController  {
        private static OjCheckpointService ojCheckpointService;

        /**
         * 静态注入service
         *
         * @param ojCheckpointService
         */
        @Autowired
        public OjCheckpointController (OjCheckpointService ojCheckpointService){
            OjCheckpointController .ojCheckpointService=ojCheckpointService;
        }

        /**
         * Delete boolean.
         *
         * @param ojCheckpoint the ojCheckpoint
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(OjCheckpoint ojCheckpoint){
                return ojCheckpointService.deleteById(ojCheckpoint);
        }

        /**
         * Insert or update boolean.
         *
         * @param ojCheckpoint the ojCheckpoint
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(OjCheckpoint ojCheckpoint){
                return ojCheckpointService.insertOrUpdate(ojCheckpoint);
        }

        /**
         * Get ojCheckpoint.
         *
         * @param ojCheckpoint the ojCheckpoint
         * @return the ojCheckpoint
         */
        @MethodAnnot
        public static OjCheckpoint get(OjCheckpoint ojCheckpoint){
                return ojCheckpointService.selectById(ojCheckpoint);
        }

        /**
         * Gets ojCheckpoint by page.
         *
         * @return the ojCheckpoint by page
         */
        @MethodAnnot
        public static Page<OjCheckpoint> getOjCheckpointByPage(Page<OjCheckpoint> page){
            return ojCheckpointService.selectPage(page,
                new EntityWrapper<OjCheckpoint>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }
        }