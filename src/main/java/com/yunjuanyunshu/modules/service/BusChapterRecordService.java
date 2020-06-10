package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.BusChapterRecord;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
public interface BusChapterRecordService extends IService<BusChapterRecord> {
            public void inOrUpBusCpRecord(User user, BusChapterRecord busChapterRecord, RespInfo respInfo);
    void finishProgress(User user, BusCourse busCourse, RespInfo respInfo);
}