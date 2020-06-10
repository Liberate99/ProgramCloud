package com.yunjuanyunshu.modules.web.system;


import com.yunjuanyunshu.modules.entity.UserRole;
import com.yunjuanyunshu.modules.service.UserRoleService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author  apple
 * @since 2017-06-28 14:53:07
 */
@Component
@ClassAnnot("system.userRole")
public class UserRoleController {
    private static UserRoleService userRoleService;
    /**
     *静态注入service
    *@param userRoleService
    */
    @Autowired
    public UserRoleController(UserRoleService userRoleService){
        UserRoleController.userRoleService=userRoleService;
    }
    /**
     * 删除记录
     *@param userRole
     */	
     @MethodAnnot
     public static boolean delete(UserRole userRole){
        return userRoleService.deleteById(userRole);
    }

    /**
     * Insert or update boolean.
     *
     * @param userRole 
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(UserRole userRole){
        return userRoleService.insertOrUpdate(userRole);
    }

    /**
     * Get userRole.
     *
     * @param userRole 
     * @return the userRole
     */
    @MethodAnnot
    public static UserRole get(UserRole userRole){
        return userRoleService.selectById(userRole);
    }
	
}