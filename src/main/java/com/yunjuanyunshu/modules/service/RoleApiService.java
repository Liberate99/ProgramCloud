package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.RoleApi;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
public interface RoleApiService extends IService<RoleApi> {

    void insertOrUpdateToRole(RoleApi roleApi, RespInfo respInfo);
}
