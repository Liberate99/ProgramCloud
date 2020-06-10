package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusProgrammingTask;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author carl
 * @since 2018-02-25
 */
public interface BusProgrammingTaskService extends IService<BusProgrammingTask> {
    boolean insertOrUpdateProgrammingTask(HttpSession session, BusProgrammingTask busProgrammingTask);
    void getBusProgrammingTaskListByChapterId(String chapterId, RespInfo respInfo);
    void getBusProgrammingTaskWithProblem(BusProgrammingTask busProgrammingTask, RespInfo respInfo);
    void deleteProgrammingTask(HttpSession session, BusProgrammingTask busProgrammingTask);
    void hasFinishProgrammingTask(User user, BusProgrammingTask busProgrammingTask, BusChapter busChapter, RespInfo respInfo);
}