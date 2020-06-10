package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.VojProblems;
import com.yunjuanyunshu.modules.service.VojProblemsService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author carl
 * @since 2017-11-15
 */
@Component
@ClassAnnot("business.vojProblems")
public class VojProblemsController {
    /**
     * 日志记录器.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VojProblemsController.class);

    private static VojProblemsService vojProblemsService;

    /**
     * 静态注入service
     *
     * @param vojProblemsService
     */
    @Autowired
    public VojProblemsController(VojProblemsService vojProblemsService) {
        VojProblemsController.vojProblemsService = vojProblemsService;
    }

    /**
     * Delete boolean.
     *
     * @param vojProblems the vojProblems
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(VojProblems vojProblems) {
        return vojProblemsService.delete(new EntityWrapper<VojProblems>().eq("problem_id", vojProblems.getProblemId()));
    }

    /**
     * Insert or update boolean.
     *
     * @param vojProblems the vojProblems
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(VojProblems vojProblems) {
        return vojProblemsService.insertOrUpdate(vojProblems);
    }

    /**
     * Get vojProblems.
     *
     * @param vojProblems the vojProblems
     * @return the vojProblems
     */
    @MethodAnnot
    public static VojProblems get(VojProblems vojProblems) {
        return vojProblemsService.selectById(vojProblems);
    }

    /**
     * Gets vojProblems by page.
     *
     * @return the vojProblems by page
     */
    @MethodAnnot
    public static Page<VojProblems> getVojProblemsByPage(Page<VojProblems> page) {
        return vojProblemsService.selectPage(page,
                new EntityWrapper<VojProblems>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    /**
     * 获取含有全部提交和通过提交的试题集
     * @param page
     */
    @MethodAnnot
    public static Page<VojProblems> getProblemsByPage(Page<VojProblems> page){
        return vojProblemsService.getProblemsByPage(page);
    }

    /**
     * 处理用户创建试题的请求.
     * @param vojProblems 试题对象
     * @param checkpointExactlyMatch 测试点是否精确匹配
     * @param problemTags 试题标签
     * @param testCases 测试用例
     * @param problemCategories 试题分类
     * @param respInfo 返回信息
     */
    @MethodAnnot
    public static void createProblem(VojProblems vojProblems, Integer checkpointExactlyMatch, List<String> problemTags,
                                      List<Map<String,String>> testCases, List<String> problemCategories, RespInfo respInfo) {
        Map<String, Object> result = vojProblemsService.createProblem(vojProblems, checkpointExactlyMatch, problemTags,
                testCases, problemCategories);

        if (!(boolean) result.get("isSuccessful")) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg(getErrorMsg(result));
            respInfo.setError("输入格式有错误");
            return ;
        }

        long problemId = (Long) result.get("problemId");
//        LOGGER.info(String.format("Problem: [ProblemId=%s] was created." , problemId));
        respInfo.setValue(problemId);
        respInfo.setMsg("创建试题成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());

    }

    /**
     * 处理用户编辑试题的请求.
     * @param vojProblems 试题对象
     * @param checkpointExactlyMatch 测试点是否精确匹配
     * @param problemTags 试题标签
     * @param testCases 测试用例
     * @param problemCategories 试题分类
     * @param respInfo 返回信息
     */
    @MethodAnnot
    public static void editProblem(VojProblems vojProblems, Integer checkpointExactlyMatch, List<String> problemTags,
                                   List<Map<String,String>> testCases, List<String> problemCategories, RespInfo respInfo) {
        Map<String, Object> result = vojProblemsService.editProblem(vojProblems, checkpointExactlyMatch, problemTags,
                testCases, problemCategories);

        if (!(boolean)result.get("isSuccessful")) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError("输入格式有错误");
            respInfo.setMsg(getErrorMsg(result));
            return;
        }

        respInfo.setValue(vojProblems.getProblemId());
        respInfo.setMsg("编辑试题成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
    }

    /**
     * 获取某一试题的相关信息
     * @param vojProblems
     * @param respInfo
     */
    @MethodAnnot
    public static void getProblemInfo(VojProblems vojProblems, RespInfo respInfo){
        vojProblemsService.getProblemInfo(vojProblems, respInfo);
    }

    private static String getErrorMsg(Map<String, Object> result) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        if ((boolean) result.get("isProblemNameEmpty")) {
            stringBuilder.append("请填写试题名称.\n");
        }
        if (!(boolean) result.get("isProblemNameLegal")) {
            stringBuilder.append("试题名称的长度不得超过128个字符.\n");
        }
        if (!(boolean) result.get("isTimeLimitLegal")) {
            stringBuilder.append("时间限制必须是大于0的整数.\n");
        }
        if (!(boolean) result.get("isMemoryLimitLegal")) {
            stringBuilder.append("内存限制必须是大于0的整数.\n");
        }
        if ((boolean) result.get("isDescriptionEmpty")) {
            stringBuilder.append("请填写试题描述.\n");
        }
        if ((boolean) result.get("isInputFormatEmpty")) {
            stringBuilder.append("请填写输入格式.\n");
        }
        if ((boolean) result.get("isOutputFormatEmpty")) {
            stringBuilder.append("请填写输出格式.\n");
        }
        if ((boolean) result.get("isInputSampleEmpty")) {
            stringBuilder.append("请填写输入样例.\n");
        }
        if ((boolean) result.get("isOutputSampleEmpty")) {
            stringBuilder.append("请填写输出样例.\n");
        }

        return stringBuilder.toString();
    }
}