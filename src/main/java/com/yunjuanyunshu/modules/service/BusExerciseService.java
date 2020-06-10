package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.BusExercise;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * Created by apple on 2018/8/3.
 */
public interface BusExerciseService extends IService<BusExercise> {
    public void insertOrUpdateExercise(BusCourse busCourse, BusChapter busChapter, BusExercise busExercise, RespInfo respInfo);

    public void getBusExercise(BusExercise busExercise, RespInfo respInfo);

//    public void getExerciseWithStatus(BusCourse busCourse, )

    void getExerciseByCourseIdAndChapterId(BusCourse busCourse, BusChapter busChapter, RespInfo respInfo);

    // 获取章节列表
    void getTreeExerciseList(BusCourse busCourse, BusChapter busChapter, RespInfo respInfo);

    void deleteExercise(BusExercise busExercise, RespInfo respInfo);

//    void getTreeChapterListWithStatus(BusCourse busCourse, User user, RespInfo respInfo);

    void getExerciseListToFront(BusCourse busCourse, BusChapter busChapter, User user, RespInfo respInfo);


}
