package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.VojProblemCategories;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunjuanyunshu.modules.entity.VojProblemCategoryRelationships;
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
public interface VojProblemCategoriesDao extends BaseMapper<VojProblemCategories> {

    List<VojProblemCategoryRelationships> getProblemCategoriesOfProblems(
            @Param(value = "problemIdLowerBound") long problemIdLowerBound,
            @Param(value = "problemIdUpperBound") long problemIdUpperBound);

    /**
     * 获取试题的分类列表.
     * @param problemId - 试题的唯一标识符.
     * @return 包含试题分类的列表
     */
    List<VojProblemCategories> getProblemCategoriesUsingProblemId(long problemId);
}