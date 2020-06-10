package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.ApiTree;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.Role;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
public interface ApiTreeService extends IService<ApiTree> {
    void getMenuList(User user, RespInfo respInfo);

    void getMenuListToRole(Role role, RespInfo respInfo);
	
}
