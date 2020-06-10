package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojProblemTags;
import com.yunjuanyunshu.modules.service.VojProblemTagsService;
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
@ClassAnnot("business.vojProblemTags")
public class VojProblemTagsController {
    private static VojProblemTagsService vojProblemTagsService;

    /**
     * 静态注入service
     *
     * @param vojProblemTagsService
     */
    @Autowired
    public VojProblemTagsController(VojProblemTagsService vojProblemTagsService) {
        VojProblemTagsController.vojProblemTagsService = vojProblemTagsService;
    }

    /**
     * Delete boolean.
     *
     * @param vojProblemTags the vojProblemTags
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(VojProblemTags vojProblemTags) {
        return vojProblemTagsService.deleteById(vojProblemTags);
    }

    /**
     * Insert or update boolean.
     *
     * @param vojProblemTags the vojProblemTags
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(VojProblemTags vojProblemTags) {
        return vojProblemTagsService.insertOrUpdate(vojProblemTags);
    }

    /**
     * Get vojProblemTags.
     *
     * @param vojProblemTags the vojProblemTags
     * @return the vojProblemTags
     */
    @MethodAnnot
    public static VojProblemTags get(VojProblemTags vojProblemTags) {
        return vojProblemTagsService.selectById(vojProblemTags);
    }

    /**
     * Gets vojProblemTags by page.
     *
     * @return the vojProblemTags by page
     */
    @MethodAnnot
    public static Page<VojProblemTags> getVojProblemTagsByPage(Page<VojProblemTags> page) {
        return vojProblemTagsService.selectPage(page,
                new EntityWrapper<VojProblemTags>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
}