package com.yunjuanyunshu.modules.web.system;


import com.yunjuanyunshu.modules.entity.RoleApi;
import com.yunjuanyunshu.modules.service.RoleApiService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author  apple
 * @since 2017-06-28 14:53:01
 */
@Component
@ClassAnnot("system.roleApi")
public class RoleApiController {
    private static RoleApiService roleApiService;
    /**
     *静态注入service
    *@param roleApiService
    */
    @Autowired
    public RoleApiController(RoleApiService roleApiService){
 RoleApiController.roleApiService=roleApiService;
 }
    /**
     * 删除记录
     *@param roleApi
     */	
     @MethodAnnot
     public static boolean delete(RoleApi roleApi){
        return roleApiService.deleteById(roleApi);
    }

    /**
     * Insert  boolean.
     *
     * @param roleApi 
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insert(RoleApi roleApi){
        return roleApiService.insert(roleApi);
    }
    /**
     *   update boolean.
     *
     * @param roleApi
     * @return the boolean
     */
    @MethodAnnot
    public static boolean update(RoleApi roleApi){
        return roleApiService.updateById(roleApi);
    }

    /**
     * Get roleApi.
     *
     * @param roleApi 
     * @return the roleApi
     */
    @MethodAnnot
    public static RoleApi get(RoleApi roleApi){
        return roleApiService.selectById(roleApi);
    }

    /**
     * 单条选项更新用户菜单
     * @param roleApi
     * @param respInfo
     */
    @MethodAnnot
    public static void insertOrUpdateToRole(RoleApi roleApi, RespInfo respInfo) {
        roleApiService.insertOrUpdateToRole(roleApi, respInfo);
    }
	
}