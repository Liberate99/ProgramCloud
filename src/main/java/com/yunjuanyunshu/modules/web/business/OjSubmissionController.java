package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjSubmission;
import com.yunjuanyunshu.modules.service.OjSubmissionService;
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
@ClassAnnot("business.ojSubmission")
public class OjSubmissionController {
    private static OjSubmissionService ojSubmissionService;

    /**
     * 静态注入service
     *
     * @param ojSubmissionService
     */
    @Autowired
    public OjSubmissionController(OjSubmissionService ojSubmissionService) {
        OjSubmissionController.ojSubmissionService = ojSubmissionService;
    }

    /**
     * Delete boolean.
     *
     * @param ojSubmission the ojSubmission
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(OjSubmission ojSubmission) {
        return ojSubmissionService.deleteById(ojSubmission);
    }

    /**
     * Insert or update boolean.
     *
     * @param ojSubmission the ojSubmission
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(OjSubmission ojSubmission) {
        return ojSubmissionService.insertOrUpdate(ojSubmission);
    }

    /**
     * Get ojSubmission.
     *
     * @param ojSubmission the ojSubmission
     * @return the ojSubmission
     */
    @MethodAnnot
    public static OjSubmission get(OjSubmission ojSubmission) {
        return ojSubmissionService.selectById(ojSubmission);
    }

    /**
     * Gets ojSubmission by page.
     *
     * @return the ojSubmission by page
     */
    @MethodAnnot
    public static Page<OjSubmission> getOjSubmissionByPage(Page<OjSubmission> page) {
        return ojSubmissionService.selectPage(page,
                new EntityWrapper<OjSubmission>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    @MethodAnnot
    public static void createSubmission(OjSubmission ojSubmission, RespInfo respInfo) {
        ojSubmissionService.createSubmission(ojSubmission, respInfo);
    }
}