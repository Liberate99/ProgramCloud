package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusTaskRecord;
import com.yunjuanyunshu.modules.service.BusTaskRecordService;
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
 * @since 2018-04-07
 */
@Component
@ClassAnnot("business.busTaskRecord")
public class BusTaskRecordController  {
        private static BusTaskRecordService busTaskRecordService;

        /**
         * 静态注入service
         *
         * @param busTaskRecordService
         */
        @Autowired
        public BusTaskRecordController (BusTaskRecordService busTaskRecordService){
            BusTaskRecordController .busTaskRecordService=busTaskRecordService;
        }

        /**
         * Delete boolean.
         *
         * @param busTaskRecord the busTaskRecord
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusTaskRecord busTaskRecord){
                return busTaskRecordService.deleteById(busTaskRecord);
        }

        /**
         * Insert or update boolean.
         *
         * @param busTaskRecord the busTaskRecord
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusTaskRecord busTaskRecord){
                return busTaskRecordService.insertOrUpdate(busTaskRecord);
        }

        /**
         * Get busTaskRecord.
         *
         * @param busTaskRecord the busTaskRecord
         * @return the busTaskRecord
         */
        @MethodAnnot
        public static BusTaskRecord get(BusTaskRecord busTaskRecord){
                return busTaskRecordService.selectById(busTaskRecord);
        }

        /**
         * Gets busTaskRecord by page.
         *
         * @return the busTaskRecord by page
         */
        @MethodAnnot
        public static Page<BusTaskRecord> getBusTaskRecordByPage(Page<BusTaskRecord> page){
            return busTaskRecordService.selectPage(page,
                new EntityWrapper<BusTaskRecord>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }
        }