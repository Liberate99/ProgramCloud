package com.yunjuanyunshu.modules.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunjuanyunshu.modules.entity.BusExercise;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dj
 * @since 2018-8-3
 */
public interface BusExerciseDao extends BaseMapper<BusExercise> {

    @Select("SELECT id,course_id AS courseId, chapter AS chapterId, title, description, code, type FROM bus_exercise e")
    public List<BusExercise> getExerciseByCourseIdAndChapterId(@Param("courseId") String courseId,@Param("chapterId") String chapterId);
}
