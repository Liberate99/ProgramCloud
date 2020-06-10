package com.yunjuanyunshu.modules.web.business;

import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.BusExercise;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusExerciseService;
import com.yunjuanyunshu.modules.util.CacheUtils;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dj on 2018/8/4.
 */
@Component
@ClassAnnot("bussiness.busExercise")
public class BusExerciseController {
    private static BusExerciseService busExerciseService;

    /**
     * 静态注入service
     *
     * @param busExerciseService
     */
    @Autowired
    public BusExerciseController(BusExerciseService busExerciseService) {
        BusExerciseController.busExerciseService = busExerciseService;
    }

    /**
     * Delete boolean.
     *
     * @param busExercise the buschapter
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(BusExercise busExercise) {
        return busExerciseService.deleteById(busExercise);
    }

//    @MethodAnnot
//    public static void deleteExercise(BusExercise busExercise, RespInfo respInfo) {
//        busExerciseService.deleteExercise(busExercise, respInfo);
//    }

    /**
     * Insert or update boolean.
     *
     * @param busExercise
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(BusExercise busExercise) {
        return busExerciseService.insertOrUpdate(busExercise);
    }

    /**
     * Get buschapter.
     *
     * @param busExercise the buschapter
     * @return the buschapter
     */
    @MethodAnnot
    public static void get(BusExercise busExercise, RespInfo respInfo) {
        busExerciseService.getBusExercise(busExercise, respInfo);
    }

    @MethodAnnot
    public static void insertOrUpdateExercise(BusCourse busCourse, BusChapter busChapter, BusExercise busExercise, RespInfo respInfo) {
        CacheUtils.put("treeExerciseList" + busCourse.getId() + busChapter.getId(), null);
        busExerciseService.insertOrUpdateExercise(busCourse, busChapter, busExercise, respInfo);
    }

    @MethodAnnot
    public static void getBusExercise(BusExercise busExercise, RespInfo respInfo) {
        busExerciseService.getBusExercise(busExercise, respInfo);
    }

//    public void getExerciseWithStatus(BusCourse busCourse, )

    @MethodAnnot
    void getExerciseByCourseIdAndChapterId(BusCourse busCourse, BusChapter busChapter, RespInfo respInfo) {
        busExerciseService.getExerciseByCourseIdAndChapterId(busCourse, busChapter, respInfo);
    }

    // 获取章节列表
    @MethodAnnot
    void getTreeExerciseList(BusCourse busCourse, BusChapter busChapter, RespInfo respInfo) {
        busExerciseService.getExerciseByCourseIdAndChapterId(busCourse, busChapter, respInfo);
    }

    @MethodAnnot
    void deleteExercise(BusExercise busExercise, RespInfo respInfo) {
        busExerciseService.deleteExercise(busExercise, respInfo);
    }

//    void getTreeChapterListWithStatus(BusCourse busCourse, User user, RespInfo respInfo);

    @MethodAnnot
    void getExerciseListToFront(BusCourse busCourse, BusChapter busChapter, User user, RespInfo respInfo) {
        busExerciseService.getExerciseListToFront(busCourse, busChapter, user, respInfo);
    }


}
