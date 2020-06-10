package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusChapterService;
import com.yunjuanyunshu.modules.util.CacheUtils;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@Component
@ClassAnnot("business.busChapter")
public class BusChapterController {
    private static BusChapterService buschapterService;

    /**
     * 静态注入service
     *
     * @param buschapterService
     */
    @Autowired
    public BusChapterController(BusChapterService buschapterService) {
        BusChapterController.buschapterService = buschapterService;
    }

    /**
     * Delete boolean.
     *
     * @param buschapter the buschapter
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(BusChapter buschapter) {
        return buschapterService.deleteById(buschapter);
    }

    @MethodAnnot
    public static void deleteChapter(BusChapter busChapter, RespInfo respInfo) {
        buschapterService.deleteChapter(busChapter, respInfo);
    }

    /**
     * Insert or update boolean.
     *
     * @param buschapter the buschapter
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(BusChapter buschapter) {
        return buschapterService.insertOrUpdate(buschapter);
    }

    /**
     * Insert or update boolean.
     *
     * @param busChapter the buschapter
     * @return the boolean
     */
    @MethodAnnot
    public static void insertOrUpdateChapter(BusCourse busCourse, BusChapter busChapter, RespInfo respInfo) {
        CacheUtils.put("treeChapterList" + busCourse.getId(), null);
        buschapterService.insertOrUpdateChapter(busCourse, busChapter, respInfo);
    }

    @MethodAnnot
    public static void getClassifyByChapter(BusChapter busChapter, RespInfo respInfo) {
        buschapterService.getClassifyByChapter(busChapter, respInfo);
    }

    /**
     * Get buschapter.
     *
     * @param buschapter the buschapter
     * @return the buschapter
     */
    @MethodAnnot
    public static void get(BusChapter buschapter, RespInfo respInfo) {
        buschapterService.getBusChapter(buschapter, respInfo);
    }

    /**
     * Gets buschapter by page.
     *
     * @return the buschapter by page
     */
    @MethodAnnot
    public static void getBusChapterByPage(BusCourse busCourse, User user, RespInfo respInfo) {
        buschapterService.getChaptersWithStatus(busCourse, user, respInfo);
    }

    @MethodAnnot
    public static void getBusChapterByPages(Page<BusChapter> page, BusCourse busCourse, User user, RespInfo respInfo) {
        buschapterService.getChaptersWithStatusByPage(page, busCourse, user, respInfo);
    }

    @MethodAnnot
    public static void getChaptersByCourseId(BusCourse busCourse, RespInfo respInfo) {
        buschapterService.getChaptersByCourseId(busCourse, respInfo);
    }

    @MethodAnnot
    public static void getTreeChapterList(BusCourse busCourse, RespInfo respInfo) {
        buschapterService.getTreeChapterList(busCourse, respInfo);
    }

    @MethodAnnot
    public static void getTreeChapterListToFront(BusCourse busCourse, User user, RespInfo respInfo) {
        buschapterService.getChapterListToFront(busCourse, user, respInfo);
    }

    @MethodAnnot
    public static void getTreeChapterListWithStatus(BusCourse busCourse, User user, RespInfo respInfo) {
        buschapterService.getTreeChapterListWithStatus(busCourse, user, respInfo);
    }

    @MethodAnnot
    public static void getBusChapterWithCourseClassify(BusChapter busChapter, RespInfo respInfo) {
        buschapterService.getBusChapterWithCourseClassify(busChapter, respInfo);
    }
}