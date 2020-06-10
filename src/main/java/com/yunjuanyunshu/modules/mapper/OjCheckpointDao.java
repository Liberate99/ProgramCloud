package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.OjCheckpoint;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
public interface OjCheckpointDao extends BaseMapper<OjCheckpoint> {

    /**
     * 创建测试点
     * @param ojCheckpoint
     * @return
     */
    int addCheckpoint(OjCheckpoint ojCheckpoint);

    /**
     * 删除某个试题的全部测试点.
     * @param problemId
     * @return
     */
    int deleteCheckpoint(long problemId);
}