package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.OjProblemTag;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunjuanyunshu.modules.entity.OjProblemTagRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author carl
 * @since 2018-02-14
 */
public interface OjProblemTagDao extends BaseMapper<OjProblemTag> {

    /**
     * 获取区间内的试题标签.
     * @param lowerId
     * @param upperId
     * @return
     */
    List<OjProblemTagRelation> getTag(@Param(value = "lowerId") long lowerId,@Param(value = "upperId") long upperId);

    /**
     * 添加试题标签.
     * @param ojProblemTag
     * @return
     */
    int addProblemTag(OjProblemTag ojProblemTag);

    List<OjProblemTag> getTagByProblemId(long problemId);
}