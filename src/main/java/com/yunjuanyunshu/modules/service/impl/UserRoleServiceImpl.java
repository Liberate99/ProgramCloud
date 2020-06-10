package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunjuanyunshu.modules.entity.Role;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.entity.UserRole;
import com.yunjuanyunshu.modules.mapper.UserRoleDao;
import com.yunjuanyunshu.modules.service.RoleService;
import com.yunjuanyunshu.modules.service.UserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.service.UserService;
import com.yunjuanyunshu.modules.util.CacheUtils;
import com.yunjuanyunshu.modules.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Override
    public Role getRoleByToken(User user) {
//        User currentUser = userService.selectOne(new EntityWrapper<User>().eq("token",user.getToken()));

        User currentUser = (User) CacheUtils.get(user.getToken());
        UserRole userRole = super.selectOne(new EntityWrapper<UserRole>().eq("user_id",currentUser.getId()));
        Role role = roleService.selectById(userRole.getRoleId());
        return role;
    }
}
