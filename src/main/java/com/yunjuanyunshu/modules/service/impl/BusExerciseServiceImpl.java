package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.BusExercise;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.mapper.BusExerciseDao;
import com.yunjuanyunshu.modules.service.BusExerciseService;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;

/**
 * Created by apple on 2018/8/3.
 */
@Service
public class BusExerciseServiceImpl extends ServiceImpl<BusExerciseDao, BusExercise> implements BusExerciseService {

//    @Autowired
//    private BusExerciseService busExerciseService;

    @Override
    public void insertOrUpdateExercise(BusCourse busCourse, BusChapter busChapter, BusExercise busExercise, RespInfo respInfo) {
        System.out.println("insertOrUpdateExercise");
    }

    @Override
    public void getBusExercise(BusExercise busExercise, RespInfo respInfo){
        System.out.println("getBusExercise");
    }

    // 获取习题列表
    @Override
    public void getTreeExerciseList(BusCourse busCourse, BusChapter busChapter, RespInfo respInfo) {
        try {
            // 获取所有习题信息
            List<BusExercise> busExerciseList = super.selectList(
                    new EntityWrapper<BusExercise>()
                            .setSqlSelect("id,title")
                            .eq("course_id", busCourse.getId())         //课程ID
                            .eq("chapter_id", busChapter.getId()));     //章节ID
            List resultList = Lists.newArrayList();
            for (BusExercise busExercise : busExerciseList) {
                Map map = Maps.newHashMap();
                map.put("id", busExercise.getId());
                map.put("title", busExercise.getTitle());
                resultList.add(map);
            }
            respInfo.setMsg("获取习题信息成功");
            respInfo.setValue(resultList);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        }catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取习题信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void getExerciseByCourseIdAndChapterId(BusCourse busCourse, BusChapter busChapter, RespInfo respInfo) {

    }

    @Override
    public void deleteExercise(BusExercise busExercise, RespInfo respInfo){
        System.out.println("deleteExercise");
    }

    /**
     * 前端获取练习题列表
     * 已修改为从中间表获取
     *
     * @param busCourse
     * @param busChapter
     * @param user
     * @param respInfo
     */
    @Override
    public void getExerciseListToFront(BusCourse busCourse, BusChapter busChapter, User user, RespInfo respInfo) {
        try {
            // 获取所有练习题信息
            user = UserUtil.getUser(user.getToken(), respInfo);
            // 通过中间表获取所有练习题信息
            List<BusExercise> busExerciseAllList = super.selectList(
                    new EntityWrapper<BusExercise>()
                        .setSqlSelect("id,parent_id AS parentId,title,course_id AS courseId,chapter AS chapter,type")
//                        .eq("")
            );


        }catch (Exception ex) {

        }
    }

}

