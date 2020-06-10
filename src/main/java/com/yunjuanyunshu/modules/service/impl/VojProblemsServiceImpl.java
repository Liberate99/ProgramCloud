package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.VojProblemCategoriesDao;
import com.yunjuanyunshu.modules.mapper.VojProblemTagsDao;
import com.yunjuanyunshu.modules.mapper.VojProblemsDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.util.SlugifyUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author carl
 * @since 2017-11-15
 */
@Service
public class VojProblemsServiceImpl extends ServiceImpl<VojProblemsDao, VojProblems> implements VojProblemsService {

    @Autowired
    private VojProblemsDao vojProblemsDao;

    @Autowired
    private VojProblemCategoriesDao vojProblemCategoriesDao;

    @Autowired
    private VojProblemCategoriesService vojProblemCategoriesService;

    @Autowired
    private VojProblemCategoryRelationshipsService vojProblemCategoryRelationshipsService;

    @Autowired
    private VojProblemTagsService vojProblemTagsService;

    @Autowired
    private VojProblemTagsDao vojProblemTagsDao;

    @Autowired
    private VojProblemCheckpointsService vojProblemCheckpointsService;

    @Autowired
    private VojProblemTagRelationshipsService vojProblemTagRelationshipsService;

    /**
     * 分页查找试题集包含全部提交数和通过提交数
     * @param page
     */
    @Override
    public Page<VojProblems> getProblemsByPage(Page<VojProblems> page) {
        List<VojProblems> vojProblems = vojProblemsDao.selectVojProblemsWithTotalAndAcList(page);

        if (vojProblems.size() > 0) {
            long problemIdLowerBound = vojProblems.get(0).getProblemId();
            long problemIdUpperBound = vojProblems.get(vojProblems.size()-1).getProblemId();
            Map<Long, List<VojProblemCategories>> problemCategoryRelationships = getProblemCategoriesOfProblems(problemIdLowerBound, problemIdUpperBound);
            Map<Long, List<VojProblemTags>> problemTagRelationships = getProblemTagsOfProblems(problemIdLowerBound, problemIdUpperBound);
            for(VojProblems vp : vojProblems) {
                List<VojProblemCategories> vojProblemCategories = problemCategoryRelationships.get(vp.getProblemId());
                List<VojProblemTags> vojProblemTags = problemTagRelationships.get(vp.getProblemId());
                vp.setProblemCategoryName("");
                vp.setProblemTagName("");
                if (vojProblemCategories != null) {
                    StringBuilder categorys= new StringBuilder();
                    for(int i=0;i< vojProblemCategories.size();i++){
                        categorys.append(vojProblemCategories.get(i).getProblemCategoryName());
                        if (i != vojProblemCategories.size() - 1) {
                            categorys.append(", ");
                        }
                    }
                    vp.setProblemCategoryName(categorys.toString());
                }
                if (vojProblemTags != null) {
                    StringBuilder tags = new StringBuilder();
                    for (int i=0;i<vojProblemTags.size();i++){
                        tags.append(vojProblemTags.get(i).getProblemTagName());
                        if (i != vojProblemTags.size() - 1) {
                            tags.append(", ");
                        }
                    }
                    vp.setProblemTagName(tags.toString());
                }
            }
        }
        page.setRecords(vojProblems);
        return page;
    }


    /**
     * 获取某个区间内各试题的分类.
     * @param problemIdLowerBound - 试题ID区间的下界
     * @param problemIdUpperBound - 试题ID区间的上界
     * @return 包含试题分类信息的列表
     */
    @Override
    public Map<Long, List<VojProblemCategories>> getProblemCategoriesOfProblems(
            long problemIdLowerBound, long problemIdUpperBound) {
        List<VojProblemCategoryRelationships> problemCategoryRelationships =
                vojProblemCategoriesDao.getProblemCategoriesOfProblems(problemIdLowerBound, problemIdUpperBound);

        Map<Long, List<VojProblemCategories>> problemCategoriesOfProblems = new HashMap<>();
        for ( VojProblemCategoryRelationships pcr : problemCategoryRelationships ) {
            long problemId = pcr.getProblemId();
            if ( !problemCategoriesOfProblems.containsKey(problemId) ) {
                problemCategoriesOfProblems.put(problemId, new ArrayList<>());
            }

            List<VojProblemCategories> problemCategories = problemCategoriesOfProblems.get(problemId);
            problemCategories.add(new VojProblemCategories(
                    pcr.getProblemCategoryId(), pcr.getProblemCategorySlug(),
                    pcr.getProblemCategoryName(), 0));
        }
        return problemCategoriesOfProblems;
    }

    /**
     * 获取某个区间内各试题的标签.
     * @param problemIdLowerBound - 试题ID区间的下界
     * @param problemIdUpperBound - 试题ID区间的上界
     * @return 包含试题分类信息的列表
     */
    @Override
    public Map<Long, List<VojProblemTags>> getProblemTagsOfProblems(
            long problemIdLowerBound, long problemIdUpperBound) {
        List<VojProblemTagRelationships> problemTagRelationships =
                vojProblemTagsDao.getProblemTagsOfProblems(problemIdLowerBound, problemIdUpperBound);

        Map<Long, List<VojProblemTags>> problemTagsOfProblems = new HashMap<>();
        for ( VojProblemTagRelationships ptr : problemTagRelationships ) {
            long problemId = ptr.getProblemId();
            if ( !problemTagsOfProblems.containsKey(problemId) ) {
                problemTagsOfProblems.put(problemId, new ArrayList<>());
            }

            List<VojProblemTags> problemTags = problemTagsOfProblems.get(problemId);
            problemTags.add(new VojProblemTags(
                    ptr.getProblemTagId(), ptr.getProblemTagSlug(),
                    ptr.getProblemTagName()));
        }
        return problemTagsOfProblems;
    }

    /**
     * 创建试题.
     * @param vojProblems 试题对象
     * @param checkpointExactlyMatch 测试点是否精确匹配
     * @param problemTags 试题标签
     * @param testCases 测试用例
     * @param problemCategories 试题分类
     */
    @Override
    public Map<String, Object> createProblem(VojProblems vojProblems, Integer checkpointExactlyMatch, List<String> problemTags,
                                             List<Map<String,String>> testCases, List<String> problemCategories) {
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) getProblemCreationResult(vojProblems);

        if ( (boolean) result.get("isSuccessful") ) {
            vojProblemsDao.createProblem(vojProblems);
            long problemId = vojProblems.getProblemId();
            createTestCases(problemId,testCases,checkpointExactlyMatch);
            createProblemCategoryRelationships(problemId,problemCategories);
            createProblemTags(problemId,problemTags);

            result.put("problemId", problemId);
        }
        return result;
    }

    /**
     * 编辑试题.
     * @param vojProblems 试题对象
     * @param checkpointExactlyMatch 测试点是否精确匹配
     * @param problemTags 试题标签
     * @param testCases 测试用例
     * @param problemCategories 试题分类
     * @return
     */
    @Override
    public Map<String, Object> editProblem(VojProblems vojProblems, Integer checkpointExactlyMatch, List<String> problemTags,
                                           List<Map<String,String>> testCases, List<String> problemCategories) {
        Map<String, Object> result = getProblemEditResult(vojProblems);

        if ( (boolean)result.get("isSuccessful") ) {
            super.update(vojProblems,new EntityWrapper<VojProblems>().eq("problem_id", vojProblems.getProblemId()));

            updateTestCases(vojProblems.getProblemId(),testCases,checkpointExactlyMatch);
            updateProblemCategoryRelationships(vojProblems.getProblemId(),problemCategories);
            updateProblemTags(vojProblems.getProblemId(),problemTags);
        }
        return result;
    }

    /**
     * 检查试题信息是否合法.
     * @param vojProblems - 待编辑的试题
     * @return 包含试题编辑结果的Map<String, Boolean>对象
     */
    private Map<String, Object> getProblemEditResult(VojProblems vojProblems) {
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) getProblemCreationResult(vojProblems);

        long problemId = vojProblems.getProblemId();
        int count = super.selectCount(new EntityWrapper<VojProblems>().eq("problem_id",problemId));
        if ( count > 0 ) {
            result.put("isProblemExists", true);
        } else {
            result.put("isProblemExists", false);
            result.put("isSuccessful", false);
        }
        return result;
    }

    /**
     * 获取某一试题的相关信息
     * @param vojProblems
     * @param respInfo
     */
    @Override
    public void getProblemInfo(VojProblems vojProblems, RespInfo respInfo) {
        vojProblems = super.selectOne(new EntityWrapper<VojProblems>().eq("problem_id",vojProblems.getProblemId()));
        if (vojProblems == null) {
            respInfo.setMsg("获取试题信息失败");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            return;
        }
        List<VojProblemCheckpoints> vojProblemCheckpoints = vojProblemCheckpointsService.selectList(
                new EntityWrapper<VojProblemCheckpoints>().eq("problem_id",vojProblems.getProblemId())
                        .orderBy("checkpoint_id",true));
        List<VojProblemCategories> vojProblemCategories = vojProblemCategoriesDao.getProblemCategoriesUsingProblemId(
                vojProblems.getProblemId());

        List<VojProblemTags> vojProblemTags = vojProblemTagsDao.getProblemTagsUsingProblemId(vojProblems.getProblemId());
        Map resultMap = Maps.newHashMap();
        resultMap.put("vojProblems",vojProblems);
        resultMap.put("vojProblemCheckpoints",vojProblemCheckpoints);
        resultMap.put("vojProblemCategories",vojProblemCategories);
        resultMap.put("vojProblemTags",vojProblemTags);
        respInfo.setMsg("获取试题信息成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setValue(resultMap);

    }

    /**
     * 检查试题信息是否合法.
     * @param problem - 待创建的试题
     * @return 包含试题创建结果的Map<String, Boolean>对象
     */
    private Map<String, ? extends Object> getProblemCreationResult(VojProblems problem) {
        Map<String, Boolean> result = new HashMap<>();
        result.put("isProblemNameEmpty", problem.getProblemName().isEmpty());
        result.put("isProblemNameLegal", isProblemNameLegal(problem.getProblemName()));
        result.put("isTimeLimitLegal", problem.getProblemTimeLimit() > 0);
        result.put("isMemoryLimitLegal", problem.getProblemMemoryLimit() > 0);
        result.put("isDescriptionEmpty", problem.getProblemDescription().isEmpty());
        result.put("isInputFormatEmpty", problem.getProblemInputFormat().isEmpty());
        result.put("isOutputFormatEmpty", problem.getProblemOutputFormat().isEmpty());
        result.put("isInputSampleEmpty", problem.getProblemSampleInput().isEmpty());
        result.put("isOutputSampleEmpty", problem.getProblemSampleOutput().isEmpty());

        boolean isSuccessful = !result.get("isProblemNameEmpty")  &&  result.get("isProblemNameLegal") &&
                result.get("isTimeLimitLegal")	&&  result.get("isMemoryLimitLegal") &&
                !result.get("isDescriptionEmpty")  && !result.get("isInputFormatEmpty") &&
                !result.get("isOutputFormatEmpty") && !result.get("isInputSampleEmpty") &&
                !result.get("isOutputSampleEmpty");
        result.put("isSuccessful", isSuccessful);
        return result;
    }

    /**
     * 检查试题名称的合法性.
     * @param problemName - 试题名称
     * @return 试题名称是否合法
     */
    private boolean isProblemNameLegal(String problemName) {
        return problemName.length() <= 128;
    }

    /**
     * 创建测试用例.
     * @param problemId - 试题的唯一标识符
     * @param testCases - 测试用例 (JSON格式)
     * @param checkpointExactlyMatch - 是否精确匹配测试用例
     */
    private void createTestCases(long problemId, List<Map<String,String>> testCases, Integer checkpointExactlyMatch) {

        for (int i= 0; i < testCases.size(); ++i) {
            Map<String, String> map = testCases.get(i);

            int score = 100 / testCases.size();
            if (i == testCases.size() - 1) {
                score = 100 - score * i;
            }

            String input = map.get("input");
            String output = map.get("output");

            VojProblemCheckpoints vojProblemCheckpoints = new VojProblemCheckpoints(problemId, i, checkpointExactlyMatch,
                    score, input, output);
            vojProblemCheckpointsService.insert(vojProblemCheckpoints);
        }
    }

    /**
     * 更新测试用例.
     * 首先删除该试题的全部的测试用例, 然后创建测试用例.
     * @param problemId - 试题的唯一标识符
     * @param testCases - 测试用例
     * @param isExactlyMatch - 是否精确匹配测试用例
     */
    private void updateTestCases(long problemId, List<Map<String,String>> testCases, Integer isExactlyMatch) {
        vojProblemCheckpointsService.delete(new EntityWrapper<VojProblemCheckpoints>().eq("problem_id",problemId));
        createTestCases(problemId, testCases, isExactlyMatch);
    }

    /**
     * 创建试题所属分类.
     * @param problemId - 试题的唯一标识符
     * @param problemCategories - 试题分类别名的JSON数组
     */
    private void createProblemCategoryRelationships(long problemId, List<String> problemCategories) {

        if (problemCategories.size() == 0) {
            problemCategories.add("uncategorized");
        }

        for (String problemCategorySlug : problemCategories) {
            VojProblemCategories pc = vojProblemCategoriesService.selectOne(new EntityWrapper<VojProblemCategories>()
                    .eq("problem_category_slug", problemCategorySlug));
            VojProblemCategoryRelationships pcr = new VojProblemCategoryRelationships(problemId, pc.getProblemCategoryId());

            vojProblemCategoryRelationshipsService.insert(pcr);
        }
    }

    /**
     * 更新试题所属分类.
     * 首先删除该试题的全部分类, 然后重新创建分类关系.
     * @param problemId - 试题的唯一标识符
     * @param problemCategories - 试题分类别名
     */
    private void updateProblemCategoryRelationships(long problemId, List<String> problemCategories) {
        vojProblemCategoryRelationshipsService.delete(new EntityWrapper<VojProblemCategoryRelationships>()
                .eq("problem_id",problemId));
        createProblemCategoryRelationships(problemId, problemCategories);
    }

    /**
     * 创建试题标签.
     * @param problemId - 试题的唯一标识符
     * @param problemTags - 试题标签的JSON数组
     */
    private void createProblemTags(long problemId, List<String> problemTags) {
        Set<String> problemTagSlugs = Sets.newHashSet();

        for (String problemTagName : problemTags) {
            String problemTagSlug = SlugifyUtils.getSlug(problemTagName);

            VojProblemTags pt = vojProblemTagsService.selectOne(new EntityWrapper<VojProblemTags>()
                    .eq("problem_tag_slug",problemTagSlug));

            if (pt == null) {
                pt = new VojProblemTags(problemTagSlug, problemTagName );
                vojProblemTagsDao.createProblemTag(pt);
            }

            if (!problemTagSlugs.contains(problemTagSlug)) {
                vojProblemTagsDao.createProblemTagRelationship(problemId,pt);
                problemTagSlugs.add(problemTagSlug);
            }
        }
    }

    /**
     * 更新试题标签.
     * 首先删除该试题的全部标签, 然后重新创建标签与试题的关系.
     * @param problemId - 试题的唯一标识符
     * @param problemTags - 试题标签的JSON数组
     */
    private void updateProblemTags(long problemId, List<String> problemTags) {
        vojProblemTagRelationshipsService.delete(new EntityWrapper<VojProblemTagRelationships>()
                .eq("problem_id",problemId));
        createProblemTags(problemId, problemTags);
    }
}