package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.VojProblemTagRelationships;
import com.yunjuanyunshu.modules.entity.VojProblemTags;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author carl
 * @since 2017-11-15
 */
public interface VojProblemTagsDao extends BaseMapper<VojProblemTags> {

    List<VojProblemTagRelationships> getProblemTagsOfProblems(
            @Param(value = "problemIdLowerBound") long problemIdLowerBound,
            @Param(value = "problemIdUpperBound") long problemIdUpperBound);

    int createProblemTag(VojProblemTags vojProblemTags);

    /**
     * 创建试题及试题标签的关系.
     * @param problemId - 试题的唯一标识符
     * @param problemTag - 试题标签对象
     */
    int createProblemTagRelationship(@Param(value="problemId") long problemId, @Param(value="problemTag") VojProblemTags problemTag);

    /**
     * 通过试题的唯一标识符获取试题标签对象的列表.
     * @param problemId - 试题的唯一标识符
     * @return 预期的试题标签对象列表
     */
    List<VojProblemTags> getProblemTagsUsingProblemId(long problemId);
}