package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.common.ResponseCode;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.OjCheckpointDao;
import com.yunjuanyunshu.modules.mapper.OjProblemCategoryDao;
import com.yunjuanyunshu.modules.mapper.OjProblemDao;
import com.yunjuanyunshu.modules.mapper.OjProblemTagDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.common.Const;
import com.yunjuanyunshu.modules.util.SlugifyUtils;
import com.yunjuanyunshu.yunfd.aop.annotation.RequestParameterCanNullAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
@Service
public class OjProblemServiceImpl extends ServiceImpl<OjProblemDao, OjProblem> implements OjProblemService {

    @Autowired
    private OjProblemDao ojProblemDao;

    @Autowired
    private OjCheckpointDao ojCheckpointDao;

    @Autowired
    private OjProblemCategoryService ojProblemCategoryService;

    @Autowired
    private OjProblemCategoryDao ojProblemCategoryDao;

    @Autowired
    private OjProblemCategoryRelationService ojProblemCategoryRelationService;

    @Autowired
    private OjProblemTagService ojProblemTagService;

    @Autowired
    private OjProblemTagDao ojProblemTagDao;

    @Autowired
    private OjProblemTagRelationService ojProblemTagRelationService;

    /**
     * 创建测试用例.
     *
     * @param session
     * @param ojProblem
     * @param tags
     * @param testCases
     * @param categories
     * @param respInfo
     */
    @Override
    public void addProblem(HttpSession session, OjProblem ojProblem, List<String> tags, List<Map<String, String>> testCases,
                           List<String> categories,
                           RespInfo respInfo) {
        User user = (User) session.getAttribute("token");

        ojProblem.setCreateBy(user.getId());
        ojProblem.setUpdateBy(user.getId());

        ojProblemDao.addProblem(ojProblem);

        long problemId = ojProblem.getId();
        createTestCases(problemId, testCases);
        createCategoriesRelation(problemId, categories);
        createProblemTags(problemId, tags);

        respInfo.setValue(problemId);
        respInfo.setMsg("创建试题成功");
        respInfo.setCode(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 更新试题.
     *
     * @param session
     * @param ojProblem
     * @param tags
     * @param testCases
     * @param categories
     * @param respInfo
     */
    @Override
    public void updateProblem(HttpSession session, OjProblem ojProblem, List<String> tags, List<Map<String, String>> testCases,
                              List<String> categories,
                              RespInfo respInfo) {
        User user = (User) session.getAttribute("token");

        ojProblem.setUpdateBy(user.getId());

        ojProblemDao.updateProblem(ojProblem);

        long problemId = ojProblem.getId();
        updateTestCases(problemId, testCases);
        updateCategoriesRelation(problemId, categories);
        updateProblemTags(problemId, tags);

        respInfo.setValue(problemId);
        respInfo.setMsg("编辑试题成功");
        respInfo.setCode(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 删除试题操作.
     *
     * @param session
     * @param ojProblem
     * @param respInfo
     */
    @Override
    public void deleteProblem(HttpSession session, OjProblem ojProblem, RespInfo respInfo) {

        ojProblem.setDelFlag(Const.DeleteFlagStatus.IS_DELETE);
        super.updateById(ojProblem);

        respInfo.setMsg("删除试题成功");
        respInfo.setCode(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 试题分页(包含属性全部提交数,通过数,试题分类,试题标签)
     *
     * @param page
     * @return
     */
    @Override
    public Page<OjProblem> getProblemByPage(HttpSession session, Page<OjProblem> page, Integer categoryId, String keyword, Integer publicType) {
        //获取当前登录用户的UserId
        User currentUser = ((User) session.getAttribute("token"));
        // 判断publicType值,默认值为1，即表示返回所有公开数据以及当前登录用户数据
        if (publicType == null) {
            publicType = 1;
        }
        List<OjProblem> ojProblems = ojProblemDao.selectProblemWithTotalAndAcList(page, categoryId == 0 ? null : categoryId, StringUtils.isBlank(keyword) ? null : keyword, currentUser.getId(), publicType);
        if (ojProblems.size() > 0) {
            long lowerId = ojProblems.get(0).getId();
            long upperId = ojProblems.get(ojProblems.size() - 1).getId();
            Map<Long, List<OjProblemCategory>> pcrMap = getCategory(lowerId, upperId);
            Map<Long, List<OjProblemTag>> ptrMap = getTag(lowerId, upperId);
            for (OjProblem pItem : ojProblems) {
                List<OjProblemCategory> pcList = pcrMap.get(pItem.getId());
                List<OjProblemTag> ptList = ptrMap.get(pItem.getId());
                pItem.setCategoryName("");
                pItem.setTagName("");
                if (pcList != null) {
                    StringBuilder category = new StringBuilder();
                    for (int i = 0; i < pcList.size(); ++i) {
                        category.append(pcList.get(i).getCategoryName());
                        if (i != pcList.size() - 1) {
                            category.append(", ");
                        }
                    }
                    pItem.setCategoryName(category.toString());
                }
                if (ptList != null) {
                    StringBuilder tag = new StringBuilder();
                    for (int i = 0; i < ptList.size(); ++i) {
                        tag.append(ptList.get(i).getTagName());
                        if (i != ptList.size() - 1) {
                            tag.append(", ");
                        }
                    }
                    pItem.setTagName(tag.toString());
                }
            }
        }
        page.setRecords(ojProblems);
        return page;
    }

    /**
     * 获取某个区间的试题分类.
     *
     * @param lowerId
     * @param upperId
     * @return
     */
    private Map<Long, List<OjProblemCategory>> getCategory(long lowerId, long upperId) {
        List<OjProblemCategoryRelation> pcrList = ojProblemCategoryDao.getCategory(lowerId, upperId);

        Map<Long, List<OjProblemCategory>> pcp = Maps.newHashMap();

        for (OjProblemCategoryRelation pcrItem : pcrList) {
            long problemId = pcrItem.getProblemId();
            if (!pcp.containsKey(problemId)) {
                pcp.put(problemId, new ArrayList<>());
            }

            List<OjProblemCategory> pcList = pcp.get(problemId);

            OjProblemCategory pc = new OjProblemCategory();
            pc.setId(pcrItem.getCategoryId());
            pc.setCategorySlug(pcrItem.getCategorySlug());
            pc.setCategoryName(pcrItem.getCategoryName());
            pc.setParentId(0);

            pcList.add(pc);
        }
        return pcp;
    }

    /**
     * 获取某个区间的试题标签.
     *
     * @param lowerId
     * @param upperId
     * @return
     */
    private Map<Long, List<OjProblemTag>> getTag(long lowerId, long upperId) {
        List<OjProblemTagRelation> ptrList = ojProblemTagDao.getTag(lowerId, upperId);

        Map<Long, List<OjProblemTag>> ptp = Maps.newHashMap();

        for (OjProblemTagRelation ptrItem : ptrList) {
            long problemId = ptrItem.getProblemId();
            if (!ptp.containsKey(problemId)) {
                ptp.put(problemId, new ArrayList<>());
            }

            List<OjProblemTag> ptList = ptp.get(problemId);

            OjProblemTag pt = new OjProblemTag();
            pt.setId(ptrItem.getTagId());
            pt.setTagSlug(ptrItem.getTagSlug());
            pt.setTagName(ptrItem.getTagName());

            ptList.add(pt);
        }
        return ptp;
    }

    /**
     * 创建测试用例.
     *
     * @param problemId
     * @param testCases
     */
    private void createTestCases(long problemId, List<Map<String, String>> testCases) {

        for (int i = 0; i < testCases.size(); ++i) {
            Map<String, String> map = testCases.get(i);

            int score = 100 / testCases.size();
            if (i == testCases.size() - 1) {
                score = 100 - score * i;
            }

            String input = map.get("input");
            String output = map.get("output");

            OjCheckpoint ojCheckpoint = new OjCheckpoint();

            ojCheckpoint.setProblemId(problemId);
            ojCheckpoint.setCheckpointNo(i);
            ojCheckpoint.setCheckpointScore(score);
            ojCheckpoint.setCheckpointInput(input);
            ojCheckpoint.setCheckpointOutput(output);

            ojCheckpointDao.addCheckpoint(ojCheckpoint);
        }
    }

    /**
     * 更新测试用例.
     *
     * @param problemId
     * @param testCases
     */
    private void updateTestCases(long problemId, List<Map<String, String>> testCases) {
        ojCheckpointDao.deleteCheckpoint(problemId);
        createTestCases(problemId, testCases);
    }

    /**
     * 创建试题所属分类.
     *
     * @param problemId
     * @param categories
     */
    private void createCategoriesRelation(long problemId, List<String> categories) {

        if (categories.size() == 0) {
            categories.add("uncategorized");
        }

        for (String categorySlug : categories) {
            OjProblemCategory pc = ojProblemCategoryService.selectOne(new EntityWrapper<OjProblemCategory>()
                    .eq("category_slug", categorySlug));

            OjProblemCategoryRelation pcr = new OjProblemCategoryRelation();
            pcr.setCategoryId(pc.getId());
            pcr.setProblemId(problemId);

            ojProblemCategoryRelationService.insert(pcr);
        }

    }

    /**
     * 更新试题所属分类
     *
     * @param problemId
     * @param categories
     */
    private void updateCategoriesRelation(long problemId, List<String> categories) {
        ojProblemCategoryRelationService.delete(new EntityWrapper<OjProblemCategoryRelation>()
                .eq("problem_id", problemId));
        createCategoriesRelation(problemId, categories);
    }

    /**
     * 创建试题标签.
     *
     * @param problemId
     * @param tags
     */
    private void createProblemTags(long problemId, List<String> tags) {
        Set<String> tagSlugs = new HashSet<>();

        for (String tagName : tags) {
            String tagSlug = SlugifyUtils.getSlug(tagName);

            OjProblemTag pt = ojProblemTagService.selectOne(new EntityWrapper<OjProblemTag>().eq("tag_slug", tagSlug));

            if (pt == null) {
                pt = new OjProblemTag();
                pt.setTagName(tagName);
                pt.setTagSlug(tagSlug);
                ojProblemTagDao.addProblemTag(pt);
            }

            if (!tagSlugs.contains(tagSlug)) {
                OjProblemTagRelation ptr = new OjProblemTagRelation();

                ptr.setProblemId(problemId);
                ptr.setTagId(pt.getId());

                ojProblemTagRelationService.insert(ptr);
                tagSlugs.add(tagSlug);
            }
        }
    }

    /**
     * 更新试题标签.
     *
     * @param problemId
     * @param tags
     */
    private void updateProblemTags(long problemId, List<String> tags) {
        ojProblemTagRelationService.delete(new EntityWrapper<OjProblemTagRelation>()
                .eq("problem_id", problemId));
        createProblemTags(problemId, tags);
    }

    /**
     * 根据试题的分类和标签查找
     *
     * @param page
     * @param categoryId
     * @param keyword
     * @return
     */
    @Override
    public Page<OjProblem> searchOjProblemByCategoryOrName(HttpSession session,Page<OjProblem> page, Integer categoryId, String keyword) {
        User user = (User) session.getAttribute("token");

        if (categoryId == 0 && StringUtils.isBlank(keyword)) {
            return super.selectPage(page,
                    new EntityWrapper<OjProblem>().setSqlSelect("id,name,has_public").eq("del_flag", 0)
                            .orderBy(page.getOrderByField(),
                                    page.isAsc()));
        }

        if (categoryId != 0) {
            OjProblemCategory pc = ojProblemCategoryDao.selectById(categoryId);
            if (pc == null && StringUtils.isBlank(keyword)) {
                return page;
            }
        }

        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        List<OjProblem> ojProblemList = ojProblemDao.selectProblemByCategoryIdAndName(
                page, categoryId == 0 ? null : categoryId, StringUtils.isBlank(keyword) ? null : keyword,user.getId());
        page.setRecords(ojProblemList);
        return page;
    }

    @Override
    public void getProblem(OjProblem ojProblem, RespInfo respInfo) {
        ojProblem = super.selectById(ojProblem);
        if (ojProblem == null) {
            respInfo.setMsg("获取试题信息失败");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            return;
        }

        List<OjCheckpoint> ojCheckpoints = ojCheckpointDao.selectList(new EntityWrapper<OjCheckpoint>()
                .eq("problem_id", ojProblem.getId()).orderBy("checkpoint_no", true));

        List<OjProblemCategory> ojProblemCategories = ojProblemCategoryDao.getCategoryByProblemId(ojProblem.getId());

        List<OjProblemTag> ojProblemTags = ojProblemTagDao.getTagByProblemId(ojProblem.getId());

        Map resultMap = Maps.newHashMap();
        resultMap.put("ojProblem", ojProblem);
        resultMap.put("testCases", ojCheckpoints);
        resultMap.put("categories", ojProblemCategories);
        resultMap.put("tags", ojProblemTags);
        respInfo.setMsg("获取试题信息成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setValue(resultMap);
    }
}