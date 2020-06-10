package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojSubmissions;
import com.yunjuanyunshu.modules.service.VojSubmissionsService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author carl
 * @since 2017-11-15
 */
@Component
@ClassAnnot("business.vojSubmissions")
public class VojSubmissionsController {
    private static VojSubmissionsService vojSubmissionsService;

    /**
     * 静态注入service
     *
     * @param vojSubmissionsService
     */
    @Autowired
    public VojSubmissionsController(VojSubmissionsService vojSubmissionsService) {
        VojSubmissionsController.vojSubmissionsService = vojSubmissionsService;
    }

    /**
     * Delete boolean.
     *
     * @param vojSubmissions the vojSubmissions
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(VojSubmissions vojSubmissions) {
        return vojSubmissionsService.deleteById(vojSubmissions);
    }

    /**
     * Insert or update boolean.
     *
     * @param vojSubmissions the vojSubmissions
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(VojSubmissions vojSubmissions) {
        return vojSubmissionsService.insertOrUpdate(vojSubmissions);
    }

    /**
     * Get vojSubmissions.
     *
     * @param vojSubmissions the vojSubmissions
     * @return the vojSubmissions
     */
    @MethodAnnot
    public static VojSubmissions get(VojSubmissions vojSubmissions) {
        return vojSubmissionsService.selectById(vojSubmissions);
    }

    /**
     * Gets vojSubmissions by page.
     *
     * @return the vojSubmissions by page
     */
    @MethodAnnot
    public static Page<VojSubmissions> getVojSubmissionsByPage(Page<VojSubmissions> page) {
        return vojSubmissionsService.selectPage(page,
                new EntityWrapper<VojSubmissions>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
}