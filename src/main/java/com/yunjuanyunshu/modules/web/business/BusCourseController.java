package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusCourse;
import com.yunjuanyunshu.modules.entity.BusTeacherInfo;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusCourseService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.RequestParameterCanNullAnnot;
import com.yunjuanyunshu.yunfd.common.util.RespInfoUtil;
import com.yunjuanyunshu.yunfd.common.util.StringUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xjz
 * @since 2017-07-07
 */
@Component
@ClassAnnot("business.busCourse")
public class BusCourseController {
    private static BusCourseService buscourseService;

    /**
     * 静态注入service
     *
     * @param buscourseService
     */
    @Autowired
    public BusCourseController(BusCourseService buscourseService) {
        BusCourseController.buscourseService = buscourseService;
    }

    /**
     * Delete boolean.
     *
     * @param buscourse the buscourse
     * @return the boolean
     */
    @MethodAnnot
    public static void delete(BusCourse buscourse, RespInfo paraRespInfo) {
        RespInfoUtil.businessError(buscourseService.deleteById(buscourse), "删除成功",
                ServiceErrorCodeEnum.SUCCESS,
                paraRespInfo);
    }

    /**
     * 选择模板新增课程
     *
     * @param busCourse the buscourse
     * @return the boolean
     */
    @MethodAnnot
    public static void insertOrUpdate(HttpSession session,BusCourse busCourse, RespInfo paraRespInfo) {
        buscourseService.insertWithTemplate(session, busCourse, paraRespInfo);
    }


    /**
     * 选择模板新增课程
     *
     * @param busCourse the buscourse
     * @return the boolean
     */
    @MethodAnnot
    public static void insertOrUpdateWithTemplate(HttpSession session,BusCourse busCourse, RespInfo paraRespInfo) {
        buscourseService.insertWithTemplate(session,busCourse, paraRespInfo);
    }

    /**
     * Get buscourse.
     *
     * @param buscourse the buscourse
     * @return the buscourse
     */
    @MethodAnnot
    public static void get(BusCourse buscourse, RespInfo paraRespInfo) {
        EntityWrapper<BusCourse> ew = new EntityWrapper<BusCourse>();
        if (buscourse.getClassify() != null) {
            ew.eq("classify", buscourse.getClassify());
        }
        if (buscourse.getId() != null) {
            ew.eq("id", buscourse.getId());
        }
        RespInfoUtil.businessError(buscourseService.selectList(ew), "查询成功",
                ServiceErrorCodeEnum.SUCCESS,
                paraRespInfo);
    }


    /**
     * Gets buscourse by page.
     *
     * @return the buscourse by page
     */
    @MethodAnnot
    public static void getCourseByPage(BusCourse buscourse, Page<BusCourse> page, RespInfo paraRespInfo) {
        EntityWrapper<BusCourse> ew = new EntityWrapper<BusCourse>();
        ew.setSqlSelect("id,title,classify,classtime,score,create_time,create_by,del_flag,has_public,pic,view,focus");
        //如果classify为空字符则查出全部
        if (StringUtils.isNotBlank(buscourse.getClassify())) {
            //ew.setEntity(buscourse);
            ew.eq("classify", buscourse.getClassify());
        }
        if (StringUtils.isNotBlank(buscourse.getTitle())) {
            ew.like("title", buscourse.getTitle());
            //buscourse.setTitle("");
            //ew.setEntity(buscourse);
        }
        RespInfoUtil.businessError(buscourseService.selectPage(page, ew.orderBy(page.getOrderByField(), page.isAsc()).eq("del_flag", "1")), "查询成功", ServiceErrorCodeEnum.SUCCESS, paraRespInfo);
    }

    /**
     *
     * @param courseStatus
     * @param hasPublic
     * @param user
     * @param page
     * @param paraRespInfo
     */
    @MethodAnnot
    public static void getCourse(@RequestParameterCanNullAnnot(true) String courseStatus,@RequestParameterCanNullAnnot(true) Integer hasPublic, User user, Page<BusCourse> page, RespInfo paraRespInfo) {
        buscourseService.getCourse(user, page, paraRespInfo, hasPublic, courseStatus);
    }

    /**
     *
     * @param buscourse
     * @param paraRespInfo
     */
    @MethodAnnot
    public static void getCourseById(BusCourse buscourse, RespInfo paraRespInfo) {
        RespInfoUtil.businessError(buscourseService.selectById(buscourse), "查询成功",
                ServiceErrorCodeEnum.SUCCESS,
                paraRespInfo);
    }


    /**
     * 用户关注课程
     *
     * @param user         the user
     * @param buscourse    the buscourse
     * @param paraRespInfo the para resp info
     * @return the buscourse
     */
    @MethodAnnot
    public static void focusCourse(User user, BusCourse buscourse, RespInfo paraRespInfo) {
        buscourseService.focusCourse(user, buscourse, paraRespInfo);
    }

    /**
     * 用户获取我创建的课程
     * @param user
     * @param page
     * @param paraRespInfo
     */
    @MethodAnnot
    public static void myFPGACourseCreatedByMyself(User user, Page<BusCourse> page, RespInfo paraRespInfo){
        buscourseService.getFPGACourseCreateByMe(user, page, paraRespInfo);
    }

    /**
     * 用户获取我的课程  未完成课程
     *
     * @param user         the user
     * @param paraRespInfo the para resp info
     * @return the buscourse
     */
    @MethodAnnot
    public static void myCourseOnLearning(User user, RespInfo paraRespInfo) {
        buscourseService.myCourse(user, false, paraRespInfo);
    }

    /**
     * 用户获取我的课程 已完成的课程
     *
     * @param user         the user
     * @param paraRespInfo the para resp info
     * @return the buscourse
     */
    @MethodAnnot
    public static void myCourseHasFinished(User user, RespInfo paraRespInfo) {
        buscourseService.myCourse(user, true, paraRespInfo);
    }

    /**
     * 推荐课程
     *
     * @param user
     * @param num
     * @param respInfo
     */
    @MethodAnnot
    public static void recommendedCourses(User user, int num, RespInfo respInfo) {
        buscourseService.recommendedCourses(user, num, respInfo);
    }

    /**
     * 获取courseDetail课程的详细信息
     *
     * @param busCourse
     * @param respInfo
     */
    @MethodAnnot
    public static void getCourseDetail(BusCourse busCourse, RespInfo respInfo) {
        buscourseService.getCourseDetail(busCourse, respInfo);
    }

    /**
     * 获取课程对应教师的详情信息
     *
     * @param busTeacherInfo
     * @param respInfo
     */
    @MethodAnnot
    public static void getTeacherInfo(BusTeacherInfo busTeacherInfo, RespInfo respInfo) {
        buscourseService.getTeacherInfo(busTeacherInfo, respInfo);
    }

    /**
     * 获取所有课程id和title键值对
     *  根据当前用户做限制
     * @param session
     * @param respInfo
     */
    @MethodAnnot
    public static void getAllCourse(HttpSession session, RespInfo respInfo) {
        buscourseService.getAllCourse(session, respInfo);
    }
}
