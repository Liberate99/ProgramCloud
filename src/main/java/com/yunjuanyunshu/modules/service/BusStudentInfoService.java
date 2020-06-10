package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.BusStudentInfo;
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
public interface BusStudentInfoService extends IService<BusStudentInfo> {

    void insertOrUpdateStu (BusStudentInfo busStudentInfo, RespInfo respInfo);

    void getStudentCollegeIDInfoFromUserID (BusStudentInfo busStudentInfo, RespInfo respInfo);
}