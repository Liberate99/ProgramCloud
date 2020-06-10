package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.*;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
public interface BusCourseRecordService extends IService<BusCourseRecord> {
    /**
     *
     * @param user
     * @param num
     * @param respInfo
     */
    void getLatestCourseRecordByUser(User user, int num, RespInfo respInfo);

    /**
     *
     * @param user
     * @param busCourse
     * @param respInfo
     */
    void getLastestChapter(User user, BusCourse busCourse, RespInfo respInfo);

    /**
     *
     * @param user
     * @param busChapter
     * @param respInfo
     */
    void setLastestChapter(User user, BusChapter busChapter, RespInfo respInfo);

    /**
     *
     * @param user
     * @param busCourse
     * @param respInfo
     */
    void beginStudy(User user, BusCourse busCourse, RespInfo respInfo);
}