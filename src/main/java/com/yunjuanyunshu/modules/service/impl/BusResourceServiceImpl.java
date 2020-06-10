package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusResource;
import com.yunjuanyunshu.modules.entity.Role;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.entity.UserRole;
import com.yunjuanyunshu.modules.mapper.BusResourceDao;
import com.yunjuanyunshu.modules.service.BusResourceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.service.RoleService;
import com.yunjuanyunshu.modules.service.UserRoleService;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@Service
public class BusResourceServiceImpl extends ServiceImpl<BusResourceDao, BusResource> implements BusResourceService {
        @Autowired
        private UserRoleService userRoleService;
        @Autowired
        private RoleService roleService;
        @Override
        public void searchBusResourceByPage(Page<BusResource> page, HttpServletRequest request, BusResource busResource, RespInfo respInfo) {

                try {
                        EntityWrapper<BusResource> ew=new EntityWrapper<BusResource>();
                        User user = UserUtil.getUser(request.getHeader("token"),respInfo);
                        UserRole userRole = userRoleService.selectOne(new EntityWrapper<UserRole>().eq("user_id",user.getId()));
                        if (userRole == null) {
                                respInfo.setMsg("用户无权限");
                                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                                return;
                        }
                        Role role = roleService.selectOne(new EntityWrapper<Role>().eq("id",userRole.getRoleId()));
                        if ("teacher".equals(role.getEnname())){
                                ew.eq("create_by",user.getId());
                        }
                        if("user".equals(role.getEnname())) {
                            return;
                        }
                        ew.like("pre", StringUtils.isEmpty(busResource.getPre())?null:busResource.getPre()).
                        like("type",  StringUtils.isEmpty(busResource.getType())?null:busResource.getType());
                        Page<BusResource> resourcePage =  super.selectPage(page,ew);
                        respInfo.setValue(resourcePage);
                } catch (Exception e) {
                        e.printStackTrace();
                }


        }
}