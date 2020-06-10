package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.OjSubmission;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
public interface OjSubmissionService extends IService<OjSubmission> {
    void createSubmission(OjSubmission ojSubmission, RespInfo respInfo);
}