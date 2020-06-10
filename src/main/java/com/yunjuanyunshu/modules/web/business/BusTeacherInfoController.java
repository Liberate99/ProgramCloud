package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusTeacherInfo;
import com.yunjuanyunshu.modules.service.BusTeacherInfoService;
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
@ClassAnnot("business.busTeacherInfo")
public class BusTeacherInfoController {
    private static BusTeacherInfoService busTeacherInfoService;

    /**
     * 静态注入service
     *
     * @param busTeacherInfoService
     */
    @Autowired
    public BusTeacherInfoController(BusTeacherInfoService busTeacherInfoService) {
        BusTeacherInfoController.busTeacherInfoService = busTeacherInfoService;
    }

    /**
     * Delete boolean.
     *
     * @param busTeacherInfo the busTeacherInfo
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(BusTeacherInfo busTeacherInfo) {
        return busTeacherInfoService.deleteById(busTeacherInfo);
    }

    /**
     * Insert or update boolean.
     *
     * @param busTeacherInfo the busTeacherInfo
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(BusTeacherInfo busTeacherInfo) {
        return busTeacherInfoService.insertOrUpdate(busTeacherInfo);
    }

    /**
     * Get busTeacherInfo.
     *
     * @param busTeacherInfo the busTeacherInfo
     * @return the busTeacherInfo
     */
    @MethodAnnot
    public static BusTeacherInfo get(BusTeacherInfo busTeacherInfo) {
        return busTeacherInfoService.selectById(busTeacherInfo);
    }

    /**
     * Gets busTeacherInfo by page.
     *
     * @return the busTeacherInfo by page
     */
    @MethodAnnot
    public static Page<BusTeacherInfo> getBusTeacherInfoByPage(Page<BusTeacherInfo> page) {
        return busTeacherInfoService.selectPage(page,
                new EntityWrapper<BusTeacherInfo>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    /**
     * 保存教师信息
     * @param busTeacherInfo
     * @param respInfo
     */
    @MethodAnnot
    public static void insertOrUpdateTeacher(BusTeacherInfo busTeacherInfo, RespInfo respInfo) {
        busTeacherInfoService.insertOrUpdateTeacher(busTeacherInfo, respInfo);
    }
}