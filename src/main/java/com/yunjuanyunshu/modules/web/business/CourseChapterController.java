package com.yunjuanyunshu.modules.web.business;


import com.yunjuanyunshu.modules.entity.CourseChapter;
import com.yunjuanyunshu.modules.service.CourseChapterService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author apple
 * @since 2017-06-28 14:53:07
 */
@Component
@ClassAnnot("business.courseChapter")
public class CourseChapterController {
    private static CourseChapterService courseChapterService;

    /**
     * 静态注入service
     *
     * @param courseChapterService
     */
    @Autowired
    public CourseChapterController(CourseChapterService courseChapterService) {
        CourseChapterController.courseChapterService = courseChapterService;
    }

    /**
     * 删除记录
     *
     * @param courseChapter
     */
    @MethodAnnot
    public static boolean delete(CourseChapter courseChapter) {
        return courseChapterService.deleteById(courseChapter);
    }

    /**
     * Insert or update boolean.
     *
     * @param courseChapter
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(CourseChapter courseChapter) {
        return courseChapterService.insertOrUpdate(courseChapter);
    }

    /**
     * Get userRole.
     *
     * @param courseChapter
     * @return the courseChapter
     */
    @MethodAnnot
    public static CourseChapter get(CourseChapter courseChapter) {
        return courseChapterService.selectById(courseChapter);
    }

}