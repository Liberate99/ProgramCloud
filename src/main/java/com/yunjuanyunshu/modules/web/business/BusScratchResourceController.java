package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusScratchResource;
import com.yunjuanyunshu.modules.service.BusScratchResourceService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author carl
 * @since 2017-12-10
 */
@Component
@ClassAnnot("business.busScratchResource")
public class BusScratchResourceController {
    private static BusScratchResourceService busScratchResourceService;

    /**
     * 静态注入service
     *
     * @param busScratchResourceService
     */
    @Autowired
    public BusScratchResourceController(BusScratchResourceService busScratchResourceService) {
        BusScratchResourceController.busScratchResourceService = busScratchResourceService;
    }

    /**
     * Delete boolean.
     *
     * @param busScratchResource the busScratchResource
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(BusScratchResource busScratchResource) {
        return busScratchResourceService.deleteById(busScratchResource);
    }

    /**
     * Insert or update boolean.
     *
     * @param busScratchResource the busScratchResource
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(BusScratchResource busScratchResource) {
        return busScratchResourceService.insertOrUpdate(busScratchResource);
    }

    /**
     * Get busScratchResource.
     *
     * @param busScratchResource the busScratchResource
     * @return the busScratchResource
     */
    @MethodAnnot
    public static BusScratchResource get(BusScratchResource busScratchResource) {
        return busScratchResourceService.selectById(busScratchResource);
    }

    /**
     * Gets busScratchResource by page.
     *
     * @return the busScratchResource by page
     */
    @MethodAnnot
    public static Page<BusScratchResource> getBusScratchResourceByPage(Page<BusScratchResource> page) {
        return busScratchResourceService.selectPage(page,
                new EntityWrapper<BusScratchResource>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    /**
     * 获取资源素材
     *
     * @param type
     * @param respInfo
     */
    @MethodAnnot
    public static void getResourceListByType(String type, RespInfo respInfo) {
        busScratchResourceService.getResourceListByType(type, respInfo);
    }
}