package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusChapter;
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
public interface BusChapterService extends IService<BusChapter> {
    public void insertOrUpdateChapter(BusCourse busCourse, BusChapter busChapter, RespInfo respInfo);

    public void getBusChapter(BusChapter busChapter, RespInfo respInfo);

    public void getChaptersWithStatus(BusCourse busCourse, User user, RespInfo respInfo);

    public void getChaptersWithStatusByPage(Page<BusChapter> page, BusCourse busCourse, User user, RespInfo respInfo);

    void getChaptersByCourseId(BusCourse busCourse, RespInfo respInfo);
    // 获取章节列表
    void getTreeChapterList(BusCourse busCourse, RespInfo respInfo);

    void getClassifyByChapter(BusChapter busChapter, RespInfo respInfo);

    void deleteChapter(BusChapter busChapter, RespInfo respInfo);

    void getTreeChapterListWithStatus(BusCourse busCourse, User user, RespInfo respInfo);

    void getChapterListToFront(BusCourse busCourse, User user, RespInfo respInfo);

    void getBusChapterWithCourseClassify(BusChapter busChapter, RespInfo respInfo);
}