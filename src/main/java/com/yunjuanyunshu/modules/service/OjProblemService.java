package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.OjProblem;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.aop.annotation.RequestParameterCanNullAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
public interface OjProblemService extends IService<OjProblem> {

    void addProblem(HttpSession session, OjProblem ojProblem, List<String> tags, List<Map<String, String>> testCases,
                    List<String> categories,
                    RespInfo respInfo);

    void updateProblem(HttpSession session, OjProblem ojProblem, List<String> tags, List<Map<String, String>> testCases,
                       List<String> categories,
                       RespInfo respInfo);

    void deleteProblem(HttpSession session, OjProblem ojProblem, RespInfo respInfo);

    Page<OjProblem> getProblemByPage(HttpSession session,Page<OjProblem> page, Integer categoryId, String keyword, Integer publicType);

    Page<OjProblem> searchOjProblemByCategoryOrName(HttpSession session,Page<OjProblem> page, Integer categoryId, String keyword);

    void getProblem(OjProblem ojProblem, RespInfo respInfo);
}