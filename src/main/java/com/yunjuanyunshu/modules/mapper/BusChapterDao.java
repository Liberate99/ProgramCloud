package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.BusChapter;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunjuanyunshu.modules.entity.EvaluateVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
public interface BusChapterDao extends BaseMapper<BusChapter> {
    @Select("SELECT * FROM bus_chapter chapter  WHERE chapter.course_id=(" +
            "  SELECT c.id FROM bus_course c WHERE c.id=(" +
            "    SELECT class.course_id FROM bus_class class WHERE id=#{classId}" +
            "  )" +
            ")")
    public List<BusChapter> getChaptersByClassId(@Param("classId") String classId);

    @Select("SELECT chapter.title as title ,sum(if((`bcr`.`status` = 1), 1, 0)) as passTime,sum(if((`bcr`.`status` = 2), 1, 0)) as unPassTime from bus_chapter_record bcr" +
            "  LEFT JOIN bus_chapter chapter ON chapter.id=bcr.chapter_id" +
            "WHERE chapter.course_id=(" +
            "  SELECT c.id FROM bus_course c WHERE c.id=(" +
            "    SELECT class.course_id FROM bus_class class WHERE id=#{classId}" +
            "  )" +
            ")  GROUP BY bcr.chapter_id")
    public List<EvaluateVO> getEvaluateByClassId(@Param("classId") String classId);

    @Select("SELECT id,parent_id AS parentId,c.course_id as courseId,title,sort,classtime,score,type FROM bus_chapter c " +
            " JOIN bus_course_chapter cc ON c.id = cc.chapter_id" +
            "  WHERE cc.course_id = #{courseId} ORDER BY sort")
    public List<BusChapter> getChaptersByCourseId(@Param("courseId") String courseId);

    @Select("SELECT id,parent_id AS parentId,c.course_id as courseId,title,sort,classtime,score,type FROM bus_chapter c " +
            " JOIN bus_course_chapter cc ON c.id = cc.chapter_id" +
            "  WHERE cc.course_id = #{courseId} And c.parent_id=#{parentId} ORDER BY sort")
    public List<BusChapter> getChaptersByCourseIdAndParent(@Param("courseId") String courseId,@Param("parentId") String parentId);


}