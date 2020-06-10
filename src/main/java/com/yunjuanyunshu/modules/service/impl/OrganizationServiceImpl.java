package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.entity.Organization;
import com.yunjuanyunshu.modules.entity.Role;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.entity.UserRole;
import com.yunjuanyunshu.modules.mapper.OrganizationDao;
import com.yunjuanyunshu.modules.service.OrganizationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.service.RoleService;
import com.yunjuanyunshu.modules.service.UserRoleService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class OrganizationServiceImpl extends ServiceImpl<OrganizationDao, Organization> implements OrganizationService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public void getCollegeSelect(RespInfo respInfo) {
        List<Organization> organizations = super.selectList(new EntityWrapper<Organization>().eq("parent_id","0"));

        respInfo.setValue(organizations);
        respInfo.setMsg("获取学校/学院信息成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
    }

    @Override
    public void getDepartmentSelect(Organization organization, RespInfo respInfo) {
        List<Organization> organizations = super.selectList(new EntityWrapper<Organization>().eq("parent_id",organization.getId()));
        respInfo.setValue(organizations);
        respInfo.setMsg("获取学校/学院信息成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
    }

    /**
     * 插入或者更新一条数据
     * @param organization
     * @param respInfo
     */
    @Override
    public void insertOrUpdateOrganization(Organization organization, RespInfo respInfo) {
        respInfo.setMsg("更新机构信息成功");
        if (organization.getId() == null) {
            organization.setCreateDate(new Date());
            respInfo.setMsg("新增机构信息成功");
        }
        organization.setUpdateDate(new Date());
        super.insertOrUpdate(organization);
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
    }

    /**
     * 获取父机构列表 id --- name 键值对应,给select标签用
     * @param respInfo
     */
    @Override
    public void getParentOrganization (RespInfo respInfo) {
        List<Organization> organizations = super.selectList(new EntityWrapper<Organization>()
                .setSqlSelect("id,name").eq("parent_id", "0").orderBy("sort",true));
        if (organizations == null) {
            respInfo.setMsg("没有父机构存在,请先增加父机构");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            return;
        }
        List<Map> mapList = Lists.newArrayList();

        for (Organization organization : organizations) {
            Map map = Maps.newHashMap();
            map.put("id",organization.getId());
            map.put("name",organization.getName());
            mapList.add(map);
        }
        respInfo.setValue(mapList);
        respInfo.setMsg("获取父机构列表成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
    }

    /**
     * 删除机构信息
     * @param organization
     * @param respInfo
     */
    @Override
    public void deleteOrganization(Organization organization, RespInfo respInfo) {
        organization = super.selectOne(new EntityWrapper<Organization>().eq("id",organization.getId()));
        if ("0".equals(organization.getParentId())) {
            int count= super.selectCount(new EntityWrapper<Organization>().eq("parent_id",organization.getId()));
            if (count > 0) {
                respInfo.setMsg("无法删除含有子机构的机构");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                return;
            }
        }
        super.deleteById(organization);
        respInfo.setMsg("删除机构信息成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
    }

    /**
     * [仅限管理员用户]获取机构信息的树型结构
     * @param respInfo
     */
    @Override
    public void getTreeOrganizationList(User user, RespInfo respInfo) {
        //权限控制
        UserRole userRole = userRoleService.selectOne(new EntityWrapper<UserRole>().eq("user_id",user.getId()));
        if (userRole == null) {
            respInfo.setMsg("用户无权限");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            return;
        }
        Role role = roleService.selectOne(new EntityWrapper<Role>().eq("id",userRole.getRoleId()));
        if (!"admin".equals(role.getEnname())){
            respInfo.setMsg("用户无权限");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            return;
        }
        //获取所有机构信息
        List<Organization> allOrganizations = super.selectList(new EntityWrapper<Organization>()
                    .setSqlSelect("id,parent_id AS parentId,name,sort").orderBy("sort",true));
        //获取一级机构信息
        List<Organization> levelOneorganizations = getOrganizationLevelOne(allOrganizations);
        respInfo.setValue(treeOrganizationsChildData(allOrganizations,levelOneorganizations));
        respInfo.setMsg("获取机构信息成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
    }

    /**
     * 获取一级机构
     * @param organizations
     * @return
     */
    private List<Organization> getOrganizationLevelOne(List<Organization> organizations) {
        List<Organization> resultList = Lists.newArrayList();
        for (Organization organization : organizations) {
            if ("0".equals(organization.getParentId())) {
                resultList.add(organization);
            }
        }
        return resultList;
    }

    /**
     * 获取某父Id下的子机构
     * @param organizations
     * @param pid
     * @return
     */
    private List<Organization> getOrganizationChild(List<Organization> organizations, String pid) {
        List<Organization> resultList = Lists.newArrayList();
        for (Organization organization : organizations) {
            if (pid.equals(organization.getParentId())) {
                resultList.add(organization);
            }
        }
        return resultList;
    }

    /**
     * 利用递归构建树型机构
     * @param allOrganizations
     * @param levelOneorganizations
     * @return
     */
    private List treeOrganizationsChildData(List<Organization> allOrganizations, List<Organization> levelOneorganizations) {
        List resultList = Lists.newArrayList();
        for (Organization organization : levelOneorganizations) {
            List<Organization> childOrganization = getOrganizationChild(allOrganizations, organization.getId());
            Map map = Maps.newHashMap();
            if (childOrganization.size() > 0) {
                map.put("children", treeOrganizationsChildData(allOrganizations,childOrganization));
            }
            map.put("id",organization.getId());
            map.put("parentId", organization.getParentId());
            map.put("name",organization.getName());
            map.put("sort", organization.getSort());
            resultList.add(map);
        }
        return resultList;
    }


}
