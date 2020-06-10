package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusClass;
import com.yunjuanyunshu.modules.entity.VwCourseRecordName;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author carl
 * @since 2017-11-02
 */
public interface VwCourseRecordNameService extends IService<VwCourseRecordName> {

        void getCourseRecordName(Page<VwCourseRecordName> page, BusClass busClass, RespInfo respInfo);
}