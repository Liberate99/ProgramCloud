package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.entity.VwClassInfo;
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
public interface VwClassInfoService extends IService<VwClassInfo> {

        void getClassInfo(User user, Page<VwClassInfo> page, RespInfo respInfo);
}