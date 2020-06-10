package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojProblemCategories;
import com.yunjuanyunshu.modules.entity.VojProblemTags;
import com.yunjuanyunshu.modules.entity.VojProblems;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author carl
 * @since 2017-11-15
 */
public interface VojProblemsService extends IService<VojProblems> {

    Page<VojProblems> getProblemsByPage(Page<VojProblems> page);
    Map<Long, List<VojProblemCategories>> getProblemCategoriesOfProblems(
            long problemIdLowerBound, long problemIdUpperBound);
    Map<Long, List<VojProblemTags>> getProblemTagsOfProblems(
            long problemIdLowerBound, long problemIdUpperBound);
    Map<String, Object> createProblem(VojProblems vojProblems, Integer checkpointExactlyMatch, List<String> problemTags,
                                      List<Map<String,String>> testCases, List<String> problemCategories);
    void getProblemInfo(VojProblems vojProblems, RespInfo respInfo);
    Map<String, Object> editProblem(VojProblems vojProblems, Integer checkpointExactlyMatch, List<String> problemTags,
                                     List<Map<String,String>> testCases, List<String> problemCategories);
}