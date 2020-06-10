package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjProblem;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.OjProblemService;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.RequestParameterCanNullAnnot;
import com.yunjuanyunshu.yunfd.common.util.UserUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
@Component
@ClassAnnot("business.ojProblem")
public class OjProblemController {
    private static OjProblemService ojProblemService;

    /**
     * 静态注入service
     *
     * @param ojProblemService
     */
    @Autowired
    public OjProblemController(OjProblemService ojProblemService) {
        OjProblemController.ojProblemService = ojProblemService;
    }

    /**
     * Delete boolean.
     *
     * @param ojProblem the ojProblem
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(OjProblem ojProblem) {
        return ojProblemService.deleteById(ojProblem);
    }

    /**
     * Insert or update boolean.
     *
     * @param ojProblem the ojProblem
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(OjProblem ojProblem) {
        return ojProblemService.insertOrUpdate(ojProblem);
    }

    @MethodAnnot
    public static void addProblem(HttpSession session, OjProblem ojProblem, List<String> tags, List<Map<String, String>> testCases,
                                  List<String> categories, RespInfo respInfo) {
        ojProblemService.addProblem(session, ojProblem, tags, testCases, categories, respInfo);
    }

    @MethodAnnot
    public static void updateProblem(HttpSession session, OjProblem ojProblem, List<String> tags, List<Map<String, String>> testCases,
                                     List<String> categories, RespInfo respInfo) {
        ojProblemService.updateProblem(session, ojProblem, tags, testCases, categories, respInfo);
    }

    @MethodAnnot
    public static void deleteProblem(HttpSession session, OjProblem ojProblem, RespInfo respInfo) {
        ojProblemService.deleteProblem(session, ojProblem, respInfo);
    }

    @MethodAnnot
    public static Page<OjProblem> getProblemByPage(HttpSession session,Page<OjProblem> page ,@RequestParameterCanNullAnnot(true)Integer categoryId, @RequestParameterCanNullAnnot(true)String keyword,@RequestParameterCanNullAnnot(true)Integer publicType) {

        return ojProblemService.getProblemByPage(session,page,categoryId,keyword,publicType);
    }
    /**
     * Get ojProblem.
     *
     * @param ojProblem the ojProblem
     * @return the ojProblem
     */
    @MethodAnnot
    public static OjProblem get(OjProblem ojProblem) {
        return ojProblemService.selectById(ojProblem);
    }

    /**
     * Gets ojProblem by page.
     *
     * @return the ojProblem by page
     */
    @MethodAnnot
    public static Page<OjProblem> getOjProblemByPage(HttpServletRequest request,Page<OjProblem> page) {
        return ojProblemService.selectPage(page,
                new EntityWrapper<OjProblem>().orderBy(page.getOrderByField(),
                        page.isAsc()).where("has_public = {0} ","1").orNew("has_public = {0} and create_by ={1} ","0", ((User)request.getSession().getAttribute("token")).getId()));
    }

    @MethodAnnot
    public static Page<OjProblem> searchOjProblemByCategoryOrName(HttpSession session,Page<OjProblem> page, Integer categoryId, String keyword) {

        return ojProblemService.searchOjProblemByCategoryOrName(session,page, categoryId, keyword);
    }

    @MethodAnnot
    public static void getProblem(OjProblem ojProblem, RespInfo respInfo) {
        ojProblemService.getProblem(ojProblem, respInfo);
    }
}