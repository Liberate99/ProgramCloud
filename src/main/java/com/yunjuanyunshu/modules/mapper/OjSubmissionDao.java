package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.OjSubmission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
public interface OjSubmissionDao extends BaseMapper<OjSubmission> {

    int createSubmission(OjSubmission ojSubmission);
}