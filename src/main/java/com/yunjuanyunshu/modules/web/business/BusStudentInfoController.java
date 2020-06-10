package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusStudentInfo;
import com.yunjuanyunshu.modules.service.BusStudentInfoService;
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
 * @since 2017-11-17
 */
@Component
@ClassAnnot("business.busStudentInfo")
public class BusStudentInfoController {
    private static BusStudentInfoService busStudentInfoService;

    /**
     * 静态注入service
     *
     * @param busStudentInfoService
     */
    @Autowired
    public BusStudentInfoController(BusStudentInfoService busStudentInfoService) {
        BusStudentInfoController.busStudentInfoService = busStudentInfoService;
    }

    /**
     * Delete boolean.
     *
     * @param busStudentInfo the busStudentInfo
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(BusStudentInfo busStudentInfo) {
        return busStudentInfoService.deleteById(busStudentInfo);
    }

    /**
     * Insert or update boolean.
     *
     * @param busStudentInfo the busStudentInfo
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(BusStudentInfo busStudentInfo) {
        return busStudentInfoService.insertOrUpdate(busStudentInfo);
    }

    /**
     * Get busStudentInfo.
     *
     * @param busStudentInfo the busStudentInfo
     * @return the busStudentInfo
     */
    @MethodAnnot
    public static BusStudentInfo get(BusStudentInfo busStudentInfo) {
        return busStudentInfoService.selectById(busStudentInfo);
    }

    /**
     * Gets busStudentInfo by page.
     *
     * @return the busStudentInfo by page
     */
    @MethodAnnot
    public static Page<BusStudentInfo> getBusStudentInfoByPage(Page<BusStudentInfo> page) {
        return busStudentInfoService.selectPage(page, new EntityWrapper<BusStudentInfo>().orderBy(page.getOrderByField(), page.isAsc()));
    }

    /**
     * 保存学生信息
     * @param busStudentInfo
     * @param respInfo
     */
    @MethodAnnot
    public static void insertOrUpdateStu(BusStudentInfo busStudentInfo, RespInfo respInfo) {
        busStudentInfoService.insertOrUpdateStu(busStudentInfo, respInfo);
    }

    @MethodAnnot
    public static void getStudentCollegeIDInfoFromUserID(BusStudentInfo busStudentInfo, RespInfo respInfo) {
        busStudentInfoService.getStudentCollegeIDInfoFromUserID(busStudentInfo, respInfo);
    }
}