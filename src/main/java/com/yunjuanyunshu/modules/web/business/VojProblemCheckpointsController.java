package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojProblemCheckpoints;
import com.yunjuanyunshu.modules.service.VojProblemCheckpointsService;
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
@ClassAnnot("business.vojProblemCheckpoints")
public class VojProblemCheckpointsController {
    private static VojProblemCheckpointsService vojProblemCheckpointsService;

    /**
     * 静态注入service
     *
     * @param vojProblemCheckpointsService
     */
    @Autowired
    public VojProblemCheckpointsController(VojProblemCheckpointsService vojProblemCheckpointsService) {
        VojProblemCheckpointsController.vojProblemCheckpointsService = vojProblemCheckpointsService;
    }

    /**
     * Delete boolean.
     *
     * @param vojProblemCheckpoints the vojProblemCheckpoints
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(VojProblemCheckpoints vojProblemCheckpoints) {
        return vojProblemCheckpointsService.deleteById(vojProblemCheckpoints);
    }

    /**
     * Insert or update boolean.
     *
     * @param vojProblemCheckpoints the vojProblemCheckpoints
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(VojProblemCheckpoints vojProblemCheckpoints) {
        return vojProblemCheckpointsService.insertOrUpdate(vojProblemCheckpoints);
    }

    /**
     * Get vojProblemCheckpoints.
     *
     * @param vojProblemCheckpoints the vojProblemCheckpoints
     * @return the vojProblemCheckpoints
     */
    @MethodAnnot
    public static VojProblemCheckpoints get(VojProblemCheckpoints vojProblemCheckpoints) {
        return vojProblemCheckpointsService.selectById(vojProblemCheckpoints);
    }

    /**
     * Gets vojProblemCheckpoints by page.
     *
     * @return the vojProblemCheckpoints by page
     */
    @MethodAnnot
    public static Page<VojProblemCheckpoints> getVojProblemCheckpointsByPage(Page<VojProblemCheckpoints> page) {
        return vojProblemCheckpointsService.selectPage(page,
                new EntityWrapper<VojProblemCheckpoints>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }
}