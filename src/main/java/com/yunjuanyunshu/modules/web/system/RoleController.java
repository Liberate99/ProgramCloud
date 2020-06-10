package com.yunjuanyunshu.modules.web.system;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.Role;
import com.yunjuanyunshu.modules.service.RoleService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author  apple
 * @since 2017-06-28 14:52:56
 */
@Component
@ClassAnnot("system.role")
public class RoleController {
    private static RoleService roleService;
    /**
     *静态注入service
    *@param roleService
    */
    @Autowired
    public RoleController(RoleService roleService){
 RoleController.roleService=roleService;
 }
    /**
     * 删除记录
     *@param role
     */	
     @MethodAnnot
     public static boolean delete(Role role){
        return roleService.deleteById(role);
    }

    /**
     * Insert or update boolean.
     *
     * @param role 
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(Role role){
        return roleService.insertOrUpdate(role);
    }

    /**
     * Get role.
     *
     * @param role 
     * @return the role
     */
    @MethodAnnot
    public static Role get(Role role){
        return roleService.selectById(role);
    }
    @MethodAnnot
    public static Page<Role> getRoleByPage(Page<Role> page) {
        return roleService.selectPage(page,
                new EntityWrapper<Role>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
    @MethodAnnot
    public static List<Role> getRoleList(Role role) {
        return roleService.selectList(new EntityWrapper<Role>().eq("del_flag",role.getDelFlag()).orderBy("role_type"));
    }
	
}