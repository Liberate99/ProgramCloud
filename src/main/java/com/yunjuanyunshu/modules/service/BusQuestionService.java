package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.BusQuestion;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 问题表 服务类
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
public interface BusQuestionService extends IService<BusQuestion> {
    void putQuestion(User user, BusQuestion busQuestion, RespInfo respInfo);
    void getCourseQuestionByPage(BusQuestion busQuestion, Page<BusQuestion> page, RespInfo respInfo);

    void recommendQuestion(User referrer, BusQuestion busQuestion, RespInfo respInfo);

    void addBonus(User user, BusQuestion busQuestion, Integer increment, RespInfo respInfo);

    /**
     * 搜索问题
     * @param busQuestion
     * @param page
     * @param key
     * @param respInfo
     */
    void searchQuestion(BusQuestion busQuestion, Page<BusQuestion> page, String key, RespInfo respInfo);

    /**
     * 删除问题
     * @param busQuestion
     * @param user
     * @param respInfo
     */
    void delete(BusQuestion busQuestion, User user, RespInfo respInfo);
}