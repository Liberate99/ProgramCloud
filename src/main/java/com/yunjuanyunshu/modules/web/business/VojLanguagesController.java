package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojLanguages;
import com.yunjuanyunshu.modules.service.VojLanguagesService;
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
@ClassAnnot("business.vojLanguages")
public class VojLanguagesController {
    private static VojLanguagesService vojLanguagesService;

    /**
     * 静态注入service
     *
     * @param vojLanguagesService
     */
    @Autowired
    public VojLanguagesController(VojLanguagesService vojLanguagesService) {
        VojLanguagesController.vojLanguagesService = vojLanguagesService;
    }

    /**
     * Delete boolean.
     *
     * @param vojLanguages the vojLanguages
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(VojLanguages vojLanguages) {
        return vojLanguagesService.deleteById(vojLanguages);
    }

    /**
     * Insert or update boolean.
     *
     * @param vojLanguages the vojLanguages
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(VojLanguages vojLanguages) {
        return vojLanguagesService.insertOrUpdate(vojLanguages);
    }

    /**
     * Get vojLanguages.
     *
     * @param vojLanguages the vojLanguages
     * @return the vojLanguages
     */
    @MethodAnnot
    public static VojLanguages get(VojLanguages vojLanguages) {
        return vojLanguagesService.selectById(vojLanguages);
    }

    /**
     * Gets vojLanguages by page.
     *
     * @return the vojLanguages by page
     */
    @MethodAnnot
    public static Page<VojLanguages> getVojLanguagesByPage(Page<VojLanguages> page) {
        return vojLanguagesService.selectPage(page,
                new EntityWrapper<VojLanguages>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
}