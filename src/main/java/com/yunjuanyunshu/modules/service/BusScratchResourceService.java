package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.BusScratchResource;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author carl
 * @since 2017-12-10
 */
public interface BusScratchResourceService extends IService<BusScratchResource> {
    void getResourceListByType(String type, RespInfo respInfo);
}