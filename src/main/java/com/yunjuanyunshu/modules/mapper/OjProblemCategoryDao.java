package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.OjProblemCategory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunjuanyunshu.modules.entity.OjProblemCategoryRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
public interface OjProblemCategoryDao extends BaseMapper<OjProblemCategory> {

    List<OjProblemCategoryRelation> getCategory(@Param(value = "lowerId") long lowerId,
                                                @Param(value = "upperId") long upperId);

    List<OjProblemCategory> getCategoryByProblemId(long problemId);
}