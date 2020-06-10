package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjProblem;
import com.yunjuanyunshu.modules.entity.OjProblemCategory;
import com.yunjuanyunshu.modules.service.OjProblemCategoryService;
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
 * @since 2018-02-12
 */
@Component
@ClassAnnot("business.ojProblemCategory")
public class OjProblemCategoryController {
    private static OjProblemCategoryService ojProblemCategoryService;

    /**
     * 静态注入service
     *
     * @param ojProblemCategoryService
     */
    @Autowired
    public OjProblemCategoryController(OjProblemCategoryService ojProblemCategoryService) {
        OjProblemCategoryController.ojProblemCategoryService = ojProblemCategoryService;
    }

    /**
     * Delete boolean.
     *
     * @param ojProblemCategory the ojProblemCategory
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(OjProblemCategory ojProblemCategory) {
        return ojProblemCategoryService.deleteById(ojProblemCategory);
    }

    /**
     * Insert or update boolean.
     *
     * @param ojProblemCategory the ojProblemCategory
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(OjProblemCategory ojProblemCategory) {
        return ojProblemCategoryService.insertOrUpdate(ojProblemCategory);
    }

    /**
     * Get ojProblemCategory.
     *
     * @param ojProblemCategory the ojProblemCategory
     * @return the ojProblemCategory
     */
    @MethodAnnot
    public static OjProblemCategory get(OjProblemCategory ojProblemCategory) {
        return ojProblemCategoryService.selectById(ojProblemCategory);
    }

    @MethodAnnot
    public static void getTreeList(RespInfo respInfo) {
        ojProblemCategoryService.getTreeList(respInfo);
    }

    /**
     * Gets ojProblemCategory by page.
     *
     * @return the ojProblemCategory by page
     */
    @MethodAnnot
    public static Page<OjProblemCategory> getOjProblemCategoryByPage(Page<OjProblemCategory> page) {
        return ojProblemCategoryService.selectPage(page,
                new EntityWrapper<OjProblemCategory>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    @MethodAnnot
    public static void getParentProblemCategory(RespInfo respInfo) {
        ojProblemCategoryService.getParentProblemCategory(respInfo);
    }

    @MethodAnnot
    public static void getAllProblemCategoryList(RespInfo respInfo) {
        ojProblemCategoryService.getAllProblemCategoryList(respInfo);
    }

}