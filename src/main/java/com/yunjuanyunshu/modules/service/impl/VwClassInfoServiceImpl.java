package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.Role;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.entity.UserRole;
import com.yunjuanyunshu.modules.entity.VwClassInfo;
import com.yunjuanyunshu.modules.mapper.VwClassInfoDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author carl
 * @since 2017-11-02
 */
@Service
public class VwClassInfoServiceImpl extends ServiceImpl<VwClassInfoDao, VwClassInfo> implements VwClassInfoService {

        @Autowired
        private RoleService roleService;

        @Autowired
        private UserRoleService userRoleService;


        @Override
        public void getClassInfo(User user, Page<VwClassInfo> page, RespInfo respInfo) {
                try{
                        Role role=roleService.selectById(userRoleService.selectOne(new EntityWrapper<UserRole>().eq("user_id",user.getId())).getRoleId());
                        if(role==null){
                                throw new Exception(ServiceErrorCodeEnum.ERROR.getErrorStr());
                        }

                        Page<VwClassInfo> vwClassInfoPage;

                        if("admin".equals(role.getEnname())){
                                EntityWrapper<VwClassInfo> ew = new EntityWrapper<VwClassInfo>();
                                vwClassInfoPage = super.selectPage(page,
                                        ew.orderBy(page.getOrderByField(),
                                                page.isAsc()));
                        }else{
                                EntityWrapper<VwClassInfo> ew = new EntityWrapper<VwClassInfo>();
                                vwClassInfoPage = super.selectPage(page,
                                        ew.eq("createBy",user.getId()).orderBy(page.getOrderByField(),
                                                page.isAsc()));
                        }
                        respInfo.setValue(vwClassInfoPage);
                        respInfo.setMsg("查询成功");
                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());

                } catch (Exception ex) {
                        respInfo.setError(ex.getMessage());
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                }
        }
}