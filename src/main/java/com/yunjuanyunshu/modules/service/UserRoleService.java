package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.Role;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.entity.UserRole;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
public interface UserRoleService extends IService<UserRole> {
	public Role getRoleByToken(User user);
}
