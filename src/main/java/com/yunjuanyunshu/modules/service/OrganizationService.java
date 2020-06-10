package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.Organization;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
public interface OrganizationService extends IService<Organization> {
    void getCollegeSelect(RespInfo respInfo);
    void getDepartmentSelect(Organization organization, RespInfo respInfo);
    void insertOrUpdateOrganization(Organization organization, RespInfo respInfo);
    void getParentOrganization (RespInfo respInfo);
    void deleteOrganization(Organization organization, RespInfo respInfo);
    void getTreeOrganizationList(User user, RespInfo respInfo);
}
