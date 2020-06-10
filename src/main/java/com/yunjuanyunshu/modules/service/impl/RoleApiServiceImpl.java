package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunjuanyunshu.modules.entity.Role;
import com.yunjuanyunshu.modules.entity.RoleApi;
import com.yunjuanyunshu.modules.mapper.RoleApiDao;
import com.yunjuanyunshu.modules.service.RoleApiService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.service.RoleService;
import com.yunjuanyunshu.modules.util.CacheUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
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
public class RoleApiServiceImpl extends ServiceImpl<RoleApiDao, RoleApi> implements RoleApiService {

    @Autowired
    private RoleApiService roleApiService;
    @Autowired
    private RoleService roleService;

    /**
     * 单条选项更新角色菜单
     * @param roleApi
     * @param respInfo
     */
    @Override
    public void insertOrUpdateToRole(RoleApi roleApi, RespInfo respInfo) {
        try {
            boolean hasOne = roleApiService.update(roleApi,new EntityWrapper<RoleApi>().eq("role_id", roleApi.getRoleId()).eq("api_tree_id", roleApi.getApiTreeId()));
            if (!hasOne) {
                roleApiService.insert(roleApi);
            }
            //清空菜单缓存
            Role role = roleService.selectById(roleApi.getRoleId());
            CacheUtils.putSys(role.getEnname(),null);
            respInfo.setValue(roleApi);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("更新用户菜单成功");
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("更新用户菜单出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }
}
