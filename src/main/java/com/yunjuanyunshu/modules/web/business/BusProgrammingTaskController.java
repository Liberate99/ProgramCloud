package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusProgrammingTask;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusProgrammingTaskService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author carl
 * @since 2018-02-25
 */
@Component
@ClassAnnot("business.busProgrammingTask")
public class BusProgrammingTaskController {
    private static BusProgrammingTaskService busProgrammingTaskService;

    /**
     * 静态注入service
     *
     * @param busProgrammingTaskService
     */
    @Autowired
    public BusProgrammingTaskController(BusProgrammingTaskService busProgrammingTaskService) {
        BusProgrammingTaskController.busProgrammingTaskService = busProgrammingTaskService;
    }

    /**
     * Delete boolean.
     *
     * @param busProgrammingTask the busProgrammingTask
     * @return the boolean
     */
    @MethodAnnot
    public static void delete(HttpSession session, BusProgrammingTask busProgrammingTask) {
        busProgrammingTaskService.deleteProgrammingTask(session, busProgrammingTask);
    }

    /**
     * Insert or update boolean.
     *
     * @param busProgrammingTask the busProgrammingTask
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(HttpSession session, BusProgrammingTask busProgrammingTask) {
        return busProgrammingTaskService.insertOrUpdateProgrammingTask(session, busProgrammingTask);
    }

    /**
     * Get busProgrammingTask.
     *
     * @param busProgrammingTask the busProgrammingTask
     * @return the busProgrammingTask
     */
    @MethodAnnot
    public static BusProgrammingTask get(BusProgrammingTask busProgrammingTask) {
        return busProgrammingTaskService.selectById(busProgrammingTask);
    }

    /**
     * Gets busProgrammingTask by page.
     *
     * @return the busProgrammingTask by page
     */
    @MethodAnnot
    public static Page<BusProgrammingTask> getBusProgrammingTaskByPage(Page<BusProgrammingTask> page) {
        return busProgrammingTaskService.selectPage(page,
                new EntityWrapper<BusProgrammingTask>().eq("del_flag", 0).orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    @MethodAnnot
    public static Page<BusProgrammingTask> getBusProgrammingTaskByPageWithChapterId(Page<BusProgrammingTask> page, String chapterId) {
        return busProgrammingTaskService.selectPage(page,
                new EntityWrapper<BusProgrammingTask>().eq("chapter_id", chapterId).eq("del_flag", 0)
                        .orderBy("sort", true));
    }

    @MethodAnnot
    public static void getBusProgrammingTaskListByChapterId(String chapterId, RespInfo respInfo) {
        busProgrammingTaskService.getBusProgrammingTaskListByChapterId(chapterId, respInfo);
    }

    @MethodAnnot
    public static void getBusProgrammingTaskWithProblem(BusProgrammingTask busProgrammingTask, RespInfo respInfo) {
        busProgrammingTaskService.getBusProgrammingTaskWithProblem(busProgrammingTask, respInfo);
    }

    /**
     * 当编程任务成功的时候执行这个函数
     * @param user
     * @param busProgrammingTask
     * @param busChapter
     */
    @MethodAnnot
    public static void hasFinishProgrammingTask(User user, BusProgrammingTask busProgrammingTask, BusChapter busChapter, RespInfo respInfo) {
        busProgrammingTaskService.hasFinishProgrammingTask(user, busProgrammingTask, busChapter, respInfo);
    }
}