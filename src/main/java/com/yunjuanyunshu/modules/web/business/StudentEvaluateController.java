package com.yunjuanyunshu.modules.web.business;/**
 * Created with IntelliJ IDEA.
 * User: xuanjiazhen
 * Date: 2018/2/28
 * Time: 上午10:24
 */

import com.yunjuanyunshu.modules.entity.BusClass;
import com.yunjuanyunshu.modules.service.AnswerLikeService;
import com.yunjuanyunshu.modules.service.EvaluateService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author unize4
 */

@Component
@ClassAnnot("business.studentEvalate")
public  class StudentEvaluateController {

    private static EvaluateService evaluateService;

    /**
     * 静态注入service
     *
     * @param evaluateService
     */
    @Autowired
    public  StudentEvaluateController (EvaluateService evaluateService){
        StudentEvaluateController.evaluateService=evaluateService;
    }

    /**
     * 统计当前班级课程
     *
     * @param busClass
     * @param respInfo
     */
    @MethodAnnot
    public static void getEvaluateInfoByClassId(BusClass busClass, RespInfo respInfo) {
        evaluateService.getEvaluateInfoByClassId(busClass, respInfo);
    }
}
