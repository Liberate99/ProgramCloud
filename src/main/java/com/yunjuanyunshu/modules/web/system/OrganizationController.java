package com.yunjuanyunshu.modules.web.system;


import com.yunjuanyunshu.modules.entity.Organization;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.OrganizationService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author apple
 * @since 2017-06-28 14:52:46
 */
@Component
@ClassAnnot("system.organization")
public class OrganizationController {
    private static OrganizationService organizationService;

    /**
     * 静态注入service
     *
     * @param organizationService
     */
    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        OrganizationController.organizationService = organizationService;
    }

    /**
     * 删除记录
     *
     * @param organization
     */
    @MethodAnnot
    public static boolean delete(Organization organization) {
        return organizationService.deleteById(organization);
    }

    /**
     * Insert or update boolean.
     *
     * @param organization
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(Organization organization) {
        return organizationService.insertOrUpdate(organization);
    }

    /**
     * Get organization.
     *
     * @param organization
     * @return the organization
     */
    @MethodAnnot
    public static Organization get(Organization organization) {
        return organizationService.selectById(organization);
    }

    @MethodAnnot
    public static void getCollegeSelect(RespInfo respInfo) {
        organizationService.getCollegeSelect(respInfo);
    }

    @MethodAnnot
    public static void getDepartmentSelect(Organization organization, RespInfo respInfo) {
        organizationService.getDepartmentSelect(organization,respInfo);
    }

    @MethodAnnot
    public static void insertOrUpdateOrganization(Organization organization, RespInfo respInfo) {
        organizationService.insertOrUpdateOrganization(organization, respInfo);
    }

    @MethodAnnot
    public static void getParentOrganization(RespInfo respInfo) {
        organizationService.getParentOrganization(respInfo);
    }

    @MethodAnnot
    public static void deleteOrganization(Organization organization, RespInfo respInfo) {
        organizationService.deleteOrganization(organization, respInfo);
    }

    @MethodAnnot
    public static void getTreeOrganizationList(User user, RespInfo respInfo) {
        organizationService.getTreeOrganizationList(user, respInfo);
    }

}