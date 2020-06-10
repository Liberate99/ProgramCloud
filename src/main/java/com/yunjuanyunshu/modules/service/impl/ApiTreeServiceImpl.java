package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.ApiTreeDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.util.CacheUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
@Service
public class ApiTreeServiceImpl extends ServiceImpl<ApiTreeDao, ApiTree> implements ApiTreeService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleApiService roleApiService;

    @Override
    public void getMenuList(User user, RespInfo respInfo) {
        try {
            Role role = userRoleService.getRoleByToken(user);
            //判断是否有缓存
            if(CacheUtils.getSys(role.getEnname())!=null){
                respInfo.setValue(CacheUtils.getSys(role.getEnname()));
                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                respInfo.setMsg("获取菜单成功");
                return;
            }
            //1能看到，0不能看到
            List<RoleApi> roleApiList= roleApiService.selectList(new EntityWrapper<RoleApi>().eq("role_id",role.getId()).eq("status",1));
            String[] treeIds = new String[roleApiList.size()];
            for(int i=0;i<roleApiList.size();i++){
                treeIds[i]=roleApiList.get(i).getApiTreeId();
            }
            List<Map<String,String>> menuTree = new ArrayList<Map<String,String>>();
            List<ApiTree> apts = new ArrayList<ApiTree>();
            for(RoleApi roleApi :roleApiList){
                //remarks 0为菜单
                ApiTree apt= super.selectOne(new EntityWrapper<ApiTree>().eq("id",roleApi.getApiTreeId()).eq("remarks","0").eq("is_show","1").eq("parent_id","0"));
                if(apt==null) {
                    continue;
                }
                apts.add(apt);
            }

            List jsonList = getInfoMenuChild(apts,treeIds);
            //设置菜单缓存
            CacheUtils.putSys(role.getEnname(),jsonList);
            respInfo.setValue(jsonList);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("获取菜单成功");
        }catch (Exception ex){
            respInfo.setValue(ex);
            respInfo.setMsg("获取菜单出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }

    }

    /**
     * Get info menu child list.
     * 获取菜单的树形结构Json数据
     * @param apiTreeList the api tree list
     * @return the list
     */
    public List getInfoMenuChild(List<ApiTree> apiTreeList, String[] treeIds){
        List<Map> listMap = new ArrayList();
        for(ApiTree apiTree :apiTreeList){
            List<ApiTree> aptList =super.selectList(new EntityWrapper<ApiTree>().eq("parent_id",apiTree.getId()).in("id",treeIds));
            Map tempMap = new HashMap();
            //如果该设备还有孩子,继续做查询,直到设备没有孩子,也就是最后一个节点
            if(aptList.size()>0){
                tempMap.put("spread", true);
                tempMap.put("icon","fa-cubes");
                tempMap.put("children", getInfoMenuChild(aptList,treeIds));
            }else{
            // tempMap.put("icon","fa-cubes");
                tempMap.put("href", apiTree.getHref());
            }
            tempMap.put("title", apiTree.getName());
            listMap.add(tempMap);
        }
        return listMap;
    }


    /**
     * 显示角色可用菜单
     * @param role
     * @param respInfo
     */
    @Override
    public void getMenuListToRole(Role role, RespInfo respInfo) {
        try {
            //得到全部可用的顶级菜单
            List<ApiTree> apts = super.selectList(new EntityWrapper<ApiTree>().eq("remarks","0").eq("is_show","1").eq("parent_id","0"));
            respInfo.setValue(getInfoMenuChildWithStatus(role, apts));
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("获取菜单成功");
        }catch (Exception ex){
            respInfo.setValue(ex);
            respInfo.setMsg("获取菜单出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    /**
     * 查询菜单与角色权限关联的status值
     * @param role
     * @param apiTreeList
     * @return
     */
    public List getInfoMenuChildWithStatus(Role role, List<ApiTree> apiTreeList){
        List<Map> listMap = new ArrayList();
        for(ApiTree apiTree :apiTreeList){
            //得到父菜单下的子菜单
            List<ApiTree> aptList =super.selectList(new EntityWrapper<ApiTree>().eq("parent_id",apiTree.getId()));
            Map tempMap = new HashMap();
            if(aptList.size()>0){
                //如果该设备还有孩子,继续做查询,直到设备没有孩子,也就是最后一个节点
                tempMap.put("children", getInfoMenuChildWithStatus(role, aptList));
            }
            //设置父菜单的键值对
            tempMap.put("id", apiTree.getId());
            tempMap.put("title", apiTree.getName());
            RoleApi ra = roleApiService.selectOne(new EntityWrapper<RoleApi>().eq("role_id", role.getId()).eq("api_tree_id", apiTree.getId()));
            //如果获取到roleApi关联的status值，就取该值，否则status返回0
            if (ra != null) {
                tempMap.put("status", ra.getStatus());
            } else {
                tempMap.put("status", "0");
            }
            listMap.add(tempMap);
        }
        return listMap;
    }


}
