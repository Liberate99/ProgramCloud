package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojJudgeResults;
import com.yunjuanyunshu.modules.service.VojJudgeResultsService;
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
@ClassAnnot("business.vojJudgeResults")
public class VojJudgeResultsController {
    private static VojJudgeResultsService vojJudgeResultsService;

    /**
     * 静态注入service
     *
     * @param vojJudgeResultsService
     */
    @Autowired
    public VojJudgeResultsController(VojJudgeResultsService vojJudgeResultsService) {
        VojJudgeResultsController.vojJudgeResultsService = vojJudgeResultsService;
    }

    /**
     * Delete boolean.
     *
     * @param vojJudgeResults the vojJudgeResults
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(VojJudgeResults vojJudgeResults) {
        return vojJudgeResultsService.deleteById(vojJudgeResults);
    }

    /**
     * Insert or update boolean.
     *
     * @param vojJudgeResults the vojJudgeResults
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(VojJudgeResults vojJudgeResults) {
        return vojJudgeResultsService.insertOrUpdate(vojJudgeResults);
    }

    /**
     * Get vojJudgeResults.
     *
     * @param vojJudgeResults the vojJudgeResults
     * @return the vojJudgeResults
     */
    @MethodAnnot
    public static VojJudgeResults get(VojJudgeResults vojJudgeResults) {
        return vojJudgeResultsService.selectById(vojJudgeResults);
    }

    /**
     * Gets vojJudgeResults by page.
     *
     * @return the vojJudgeResults by page
     */
    @MethodAnnot
    public static Page<VojJudgeResults> getVojJudgeResultsByPage(Page<VojJudgeResults> page) {
        return vojJudgeResultsService.selectPage(page,
                new EntityWrapper<VojJudgeResults>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
}