package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojProblemTagRelationships;
import com.yunjuanyunshu.modules.service.VojProblemTagRelationshipsService;
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
@ClassAnnot("business.vojProblemTagRelationships")
public class VojProblemTagRelationshipsController {
    private static VojProblemTagRelationshipsService vojProblemTagRelationshipsService;

    /**
     * 静态注入service
     *
     * @param vojProblemTagRelationshipsService
     */
    @Autowired
    public VojProblemTagRelationshipsController(VojProblemTagRelationshipsService vojProblemTagRelationshipsService) {
        VojProblemTagRelationshipsController.vojProblemTagRelationshipsService = vojProblemTagRelationshipsService;
    }

    /**
     * Delete boolean.
     *
     * @param vojProblemTagRelationships the vojProblemTagRelationships
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(VojProblemTagRelationships vojProblemTagRelationships) {
        return vojProblemTagRelationshipsService.deleteById(vojProblemTagRelationships);
    }

    /**
     * Insert or update boolean.
     *
     * @param vojProblemTagRelationships the vojProblemTagRelationships
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(VojProblemTagRelationships vojProblemTagRelationships) {
        return vojProblemTagRelationshipsService.insertOrUpdate(vojProblemTagRelationships);
    }

    /**
     * Get vojProblemTagRelationships.
     *
     * @param vojProblemTagRelationships the vojProblemTagRelationships
     * @return the vojProblemTagRelationships
     */
    @MethodAnnot
    public static VojProblemTagRelationships get(VojProblemTagRelationships vojProblemTagRelationships) {
        return vojProblemTagRelationshipsService.selectById(vojProblemTagRelationships);
    }

    /**
     * Gets vojProblemTagRelationships by page.
     *
     * @return the vojProblemTagRelationships by page
     */
    @MethodAnnot
    public static Page<VojProblemTagRelationships> getVojProblemTagRelationshipsByPage(Page<VojProblemTagRelationships> page) {
        return vojProblemTagRelationshipsService.selectPage(page,
                new EntityWrapper<VojProblemTagRelationships>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
}