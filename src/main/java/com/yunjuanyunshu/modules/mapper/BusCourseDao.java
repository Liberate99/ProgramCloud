package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.BusCourse;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xjz
 * @since 2017-07-07
 */
public interface BusCourseDao extends BaseMapper<BusCourse> {

    /**
     * 获取历史课程记录
     * @param userId
     * @param num
     * @return
     */
    List<BusCourse> getLatestCourseRecordByUser(@Param("userId") String userId,@Param("num") int num);

    @Select("INSERT INTO bus_course_chapter  (`course_id`,`chapter_id`)(" +
            "SELECT #{courseId},  chapter_id FROM bus_course_chapter  WHERE course_id = #{templateCourseId})")
    Integer insertCourse2Chapter(@Param("courseId") String courseId,@Param("templateCourseId") String templateCourseId);
}