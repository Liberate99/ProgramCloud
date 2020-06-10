package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojProblemCategories;
import com.yunjuanyunshu.modules.service.VojProblemCategoriesService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
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
@ClassAnnot("business.vojProblemCategories")
public class VojProblemCategoriesController {
    private static VojProblemCategoriesService vojProblemCategoriesService;

    /**
     * 静态注入service
     *
     * @param vojProblemCategoriesService
     */
    @Autowired
    public VojProblemCategoriesController(VojProblemCategoriesService vojProblemCategoriesService) {
        VojProblemCategoriesController.vojProblemCategoriesService = vojProblemCategoriesService;
    }

    /**
     * Delete boolean.
     *
     * @param vojProblemCategories the vojProblemCategories
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(VojProblemCategories vojProblemCategories) {
        return vojProblemCategoriesService.delete(new EntityWrapper<VojProblemCategories>()
                .eq("problem_category_id", vojProblemCategories.getProblemCategoryId()));
    }

    /**
     * Insert or update boolean.
     *
     * @param vojProblemCategories the vojProblemCategories
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(VojProblemCategories vojProblemCategories) {
        if (vojProblemCategories.getProblemCategoryId() == null) {
            return vojProblemCategoriesService.insert(vojProblemCategories);
        }
        return vojProblemCategoriesService.update(vojProblemCategories, new EntityWrapper<VojProblemCategories>()
                .eq("problem_category_id",vojProblemCategories.getProblemCategoryId()));
    }

    /**
     * Get vojProblemCategories.
     *
     * @param vojProblemCategories the vojProblemCategories
     * @return the vojProblemCategories
     */
    @MethodAnnot
    public static VojProblemCategories get(VojProblemCategories vojProblemCategories) {
        return vojProblemCategoriesService.selectById(vojProblemCategories);
    }

    /**
     * Gets vojProblemCategories by page.
     *
     * @return the vojProblemCategories by page
     */
    @MethodAnnot
    public static Page<VojProblemCategories> getVojProblemCategoriesByPage(Page<VojProblemCategories> page) {
        return vojProblemCategoriesService.selectPage(page,
                new EntityWrapper<VojProblemCategories>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    @MethodAnnot
    public static void getParentProblemCategories(RespInfo respInfo) {
        vojProblemCategoriesService.getParentProblemCategories(respInfo);
    }

    @MethodAnnot
    public static void getTreeProblemCategoriesList(RespInfo respInfo){
        vojProblemCategoriesService.getTreeProblemCategoriesList(respInfo);
    }
}