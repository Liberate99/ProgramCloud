package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojProblemCategoryRelationships;
import com.yunjuanyunshu.modules.service.VojProblemCategoryRelationshipsService;
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
@ClassAnnot("business.vojProblemCategoryRelationships")
public class VojProblemCategoryRelationshipsController {
    private static VojProblemCategoryRelationshipsService vojProblemCategoryRelationshipsService;

    /**
     * 静态注入service
     *
     * @param vojProblemCategoryRelationshipsService
     */
    @Autowired
    public VojProblemCategoryRelationshipsController(VojProblemCategoryRelationshipsService vojProblemCategoryRelationshipsService) {
        VojProblemCategoryRelationshipsController.vojProblemCategoryRelationshipsService = vojProblemCategoryRelationshipsService;
    }

    /**
     * Delete boolean.
     *
     * @param vojProblemCategoryRelationships the vojProblemCategoryRelationships
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(VojProblemCategoryRelationships vojProblemCategoryRelationships) {
        return vojProblemCategoryRelationshipsService.deleteById(vojProblemCategoryRelationships);
    }

    /**
     * Insert or update boolean.
     *
     * @param vojProblemCategoryRelationships the vojProblemCategoryRelationships
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(VojProblemCategoryRelationships vojProblemCategoryRelationships) {
        return vojProblemCategoryRelationshipsService.insertOrUpdate(vojProblemCategoryRelationships);
    }

    /**
     * Get vojProblemCategoryRelationships.
     *
     * @param vojProblemCategoryRelationships the vojProblemCategoryRelationships
     * @return the vojProblemCategoryRelationships
     */
    @MethodAnnot
    public static VojProblemCategoryRelationships get(VojProblemCategoryRelationships vojProblemCategoryRelationships) {
        return vojProblemCategoryRelationshipsService.selectById(vojProblemCategoryRelationships);
    }

    /**
     * Gets vojProblemCategoryRelationships by page.
     *
     * @return the vojProblemCategoryRelationships by page
     */
    @MethodAnnot
    public static Page<VojProblemCategoryRelationships> getVojProblemCategoryRelationshipsByPage(Page<VojProblemCategoryRelationships> page) {
        return vojProblemCategoryRelationshipsService.selectPage(page,
                new EntityWrapper<VojProblemCategoryRelationships>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
}