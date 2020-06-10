package com.yunjuanyunshu.modules.web.system;


import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.ApiTree;
import com.yunjuanyunshu.modules.entity.Role;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.ApiTreeService;
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
 * @since 2017-06-28 14:51:25
 */
@Component
@ClassAnnot("system.apiTree")
public class ApiTreeController {
    private static ApiTreeService apiTreeService;
    /**
     *静态注入service
    *@param apiTreeService
    */
    @Autowired
    public ApiTreeController(ApiTreeService apiTreeService){
        ApiTreeController.apiTreeService=apiTreeService;
 }
    /**
     * 删除记录
     *@param apiTree
     */	
     @MethodAnnot
     public static boolean delete(ApiTree apiTree){
        return apiTreeService.deleteById(apiTree);
    }

    /**
     * Insert or update boolean.
     *
     * @param apiTree 
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(ApiTree apiTree){

        return apiTreeService.insertOrUpdate(apiTree);
    }

    /**
     * Get apiTree.
     *
     * @param apiTree 
     * @return the apiTree
     */
    @MethodAnnot
    public static ApiTree get(ApiTree apiTree){
        return apiTreeService.selectById(apiTree);
    }
    /**
     * 获取树形菜单Json数据
     *
     * @param user
     * @param respInfo
     */
    @MethodAnnot
    public static void getMenuTree(User user, RespInfo respInfo){
          apiTreeService.getMenuList(user,respInfo);
    }

    @MethodAnnot
    public static Page<ApiTree> getMenuByPage(Page<ApiTree> page) {
        return apiTreeService.selectPage(page);
    }

    /**
     * 显示角色可用菜单
     * @param role
     * @param respInfo
     */
    @MethodAnnot
    public static void getMenuListToRole(Role role, RespInfo respInfo) {
        apiTreeService.getMenuListToRole(role, respInfo);
    }

}