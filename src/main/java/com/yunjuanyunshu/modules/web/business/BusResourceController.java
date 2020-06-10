package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusResource;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusResourceService;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.common.util.UserUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@Component
@ClassAnnot("business.busResource")
public class BusResourceController {
    private static BusResourceService busresourceService;

    /**
     * 静态注入service
     *
     * @param busresourceService
     */
    @Autowired
    public BusResourceController(BusResourceService busresourceService) {
        BusResourceController.busresourceService = busresourceService;
    }

    /**
     * Delete boolean.
     *
     * @param busresource the busresource
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(BusResource busresource) {
        return busresourceService.deleteById(busresource);
    }

    /**
     * Insert or update boolean.
     *
     * @param busresource the busresource
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(BusResource busresource) {
        return busresourceService.insertOrUpdate(busresource);
    }

    /**
     * Get busresource.
     *
     * @param busresource the busresource
     * @return the busresource
     */
    @MethodAnnot
    public static BusResource get(BusResource busresource) {
        return busresourceService.selectById(busresource);
    }

    /**
     * Gets busresource by page.
     *
     * @return the busresource by page
     */
    @MethodAnnot
    public static Page<BusResource> getBusResourceByPage(Page<BusResource> page) {
        return busresourceService.selectPage(page,
                new EntityWrapper<BusResource>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    @MethodAnnot
    public static void searchBusResourceByPage(Page<BusResource> page, HttpServletRequest request, BusResource busResource, RespInfo respInfo) {
        busresourceService.searchBusResourceByPage(page, request, busResource, respInfo);

    }
}