package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.VwCourseRecordName;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author carl
 * @since 2017-11-02
 */
public interface VwCourseRecordNameDao extends BaseMapper<VwCourseRecordName> {
//        @Select("SELECT count(q.id) FROM bus_question q WHERE q.course_id = (" +
//                "    SELECT c.course_id from bus_class c WHERE c.id=#{classId}" +
//                ") AND q.create_by = #{userId}")
//        public Integer getQuestionTime(@Param("classId") String classId, @Param("userId") String userId);

//        public Integer getAnswerTime();
//        public Integer getRecommenddedTime();
//        public Integer getAcceptedTime();

        }