package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusChapterRecord;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusChapterRecordService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@Component
@ClassAnnot("business.busChapterRecord")
public class BusChapterRecordController  {
        private static BusChapterRecordService buschapterrecordService;

        /**
         * 静态注入service
         *
         * @param buschapterrecordService
         */
        @Autowired
        public BusChapterRecordController (BusChapterRecordService buschapterrecordService){
            BusChapterRecordController .buschapterrecordService=buschapterrecordService;
        }

        /**
         * Delete boolean.
         *
         * @param buschapterrecord the buschapterrecord
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusChapterRecord buschapterrecord){
                return buschapterrecordService.deleteById(buschapterrecord);
        }

        /**
         * Insert or update boolean.
         *
         * @param buschapterrecord the buschapterrecord
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusChapterRecord buschapterrecord){
                return buschapterrecordService.insertOrUpdate(buschapterrecord);
        }
        /**
         * Insert or update boolean.
         *
         * @param buschapterrecord the buschapterrecord
         * @return the boolean
         */
        @MethodAnnot
        public static boolean finishBusCha(BusChapterRecord buschapterrecord){
                BusChapterRecord bctr=buschapterrecordService.selectOne(new EntityWrapper<BusChapterRecord>()
                        .eq("user_id",buschapterrecord.getUserId())
                        .eq("chapter_id",buschapterrecord.getChapterId()));
                if(bctr==null){
                        buschapterrecord.setStatus(1);
                        buschapterrecord.setFinishTime(new Date());
                        buschapterrecord.setCreateBy(buschapterrecord.getUserId());
                        buschapterrecord.setCreateTime(new Date());
                        return buschapterrecordService.insertOrUpdate(buschapterrecord);
                }else {
                        return false;
                }

        }

        /**
         * Get buschapterrecord.
         *
         * @param buschapterrecord the buschapterrecord
         * @return the buschapterrecord
         */
        @MethodAnnot
        public static BusChapterRecord get(BusChapterRecord buschapterrecord){
                return buschapterrecordService.selectById(buschapterrecord);
        }

        /**
         * Gets buschapterrecord by page.
         *
         * @return the buschapterrecord by page
         */
        @MethodAnnot
        public static Page<BusChapterRecord> getCpRecordByPage(Page<BusChapterRecord> page){
            return buschapterrecordService.selectPage(page,
                new EntityWrapper<BusChapterRecord>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }

        /**
         * In or up bus cp record.
         * 更新用户学习章节记录
         * @param user             the user
         * @param busChapter       the bus chapter
         * @param busChapterRecord the bus chapter record
         * @param respInfo         the resp info
         */
        @MethodAnnot
        public static void inOrUpBusCpRecord(User user, BusChapter busChapter, BusChapterRecord busChapterRecord, RespInfo respInfo){
                 buschapterrecordService.inOrUpBusCpRecord(user,busChapterRecord,respInfo);
        }

        /**
         * 查看课程记录进度条
         * @param user
         * @param busCourse
         * @param respInfo
         */
        @MethodAnnot
        public static void finishProgress(User user, BusCourse busCourse, RespInfo respInfo){
                buschapterrecordService.finishProgress(user,busCourse,respInfo);
        }
}