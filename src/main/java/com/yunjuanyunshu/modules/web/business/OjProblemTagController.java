package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjProblemTag;
import com.yunjuanyunshu.modules.service.OjProblemTagService;
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
 * @since 2018-02-14
 */
@Component
@ClassAnnot("business.ojProblemTag")
public class OjProblemTagController {
    private static OjProblemTagService ojProblemTagService;

    /**
     * 静态注入service
     *
     * @param ojProblemTagService
     */
    @Autowired
    public OjProblemTagController(OjProblemTagService ojProblemTagService) {
        OjProblemTagController.ojProblemTagService = ojProblemTagService;
    }

    /**
     * Delete boolean.
     *
     * @param ojProblemTag the ojProblemTag
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(OjProblemTag ojProblemTag) {
        return ojProblemTagService.deleteById(ojProblemTag);
    }

    /**
     * Insert or update boolean.
     *
     * @param ojProblemTag the ojProblemTag
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(OjProblemTag ojProblemTag) {
        return ojProblemTagService.insertOrUpdate(ojProblemTag);
    }

    /**
     * Get ojProblemTag.
     *
     * @param ojProblemTag the ojProblemTag
     * @return the ojProblemTag
     */
    @MethodAnnot
    public static OjProblemTag get(OjProblemTag ojProblemTag) {
        return ojProblemTagService.selectById(ojProblemTag);
    }

    /**
     * Gets ojProblemTag by page.
     *
     * @return the ojProblemTag by page
     */
    @MethodAnnot
    public static Page<OjProblemTag> getOjProblemTagByPage(Page<OjProblemTag> page) {
        return ojProblemTagService.selectPage(page,
                new EntityWrapper<OjProblemTag>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
}