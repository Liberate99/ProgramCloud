package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.BusTeacherInfo;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author carl
 * @since 2017-11-17
 */
public interface BusTeacherInfoService extends IService<BusTeacherInfo> {
    void insertOrUpdateTeacher(BusTeacherInfo busTeacherInfo, RespInfo respInfo);
}