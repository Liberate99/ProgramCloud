package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusCodeRecord;
import com.yunjuanyunshu.modules.entity.BusScratchFile;
import com.yunjuanyunshu.modules.service.BusCodeRecordService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xjz
 * @since 2017-07-18
 */
@Component
@ClassAnnot("business.busCodeRecord")
public class BusCodeRecordController {
    private static BusCodeRecordService buscoderecordService;

    /**
     * 静态注入service
     *
     * @param buscoderecordService
     */
    @Autowired
    public BusCodeRecordController(BusCodeRecordService buscoderecordService) {
        BusCodeRecordController.buscoderecordService = buscoderecordService;
    }

    /**
     * Delete boolean.
     *
     * @param buscoderecord the buscoderecord
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(BusCodeRecord buscoderecord) {
        return buscoderecordService.deleteById(buscoderecord);
    }

    /**
     * Insert or update boolean.
     *
     * @param buscoderecord the buscoderecord
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(BusCodeRecord buscoderecord) {
        if ( buscoderecord.getId() == null) {
            buscoderecord.setCreateDate(new Date());
            return buscoderecordService.insert(buscoderecord);
        }
        buscoderecord.setUpdateDate(new Date());
        return buscoderecordService.update(buscoderecord,
                new EntityWrapper<BusCodeRecord>().eq("id",buscoderecord.getId()));
    }

    @MethodAnnot
    public static void insertScratch(BusScratchFile busScratchFile,BusCodeRecord busCodeRecord, RespInfo respInfo) {
         buscoderecordService.insertScratch(busScratchFile,busCodeRecord,respInfo);
    }

    /**
     * Get buscoderecord.
     *
     * @param buscoderecord the buscoderecord
     * @return the buscoderecord
     */
    @MethodAnnot
    public static BusCodeRecord get(BusCodeRecord buscoderecord) {
        return buscoderecordService.selectById(buscoderecord);
    }

    /**
     * Gets buscoderecord by page.
     *
     * @return the buscoderecord by page
     */
    @MethodAnnot
    public static Page<BusCodeRecord> getBcRecordByPage(Page<BusCodeRecord> page) {
        return buscoderecordService.selectPage(page,
                new EntityWrapper<BusCodeRecord>().orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    @MethodAnnot
    public static void execCplus(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue, RespInfo respInfo) {
        buscoderecordService.execCplus(request, busCodeRecord, inputValue, respInfo);
    }

    @MethodAnnot
    public static void execJava(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue, RespInfo respInfo) {
        buscoderecordService.execJava(request, busCodeRecord, inputValue, respInfo);
    }

    @MethodAnnot
    public static void execPy(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue, RespInfo respInfo) {
        buscoderecordService.execPy(request, busCodeRecord, inputValue, respInfo);
    }

    @MethodAnnot
    public static void execHtml(HttpServletRequest request, BusCodeRecord busCodeRecord, RespInfo respInfo) {
        buscoderecordService.execHtml(request, busCodeRecord, respInfo);
    }

    @MethodAnnot
    public static void getRecordByCIdAndUId(BusCodeRecord busCodeRecord, RespInfo respInfo) {
        buscoderecordService.getRecordByCIdAndUId(busCodeRecord, respInfo);
    }
    @MethodAnnot
    public static void getRecordByCIdAndUIdForScratch(BusCodeRecord busCodeRecord, RespInfo respInfo) {
        buscoderecordService.getRecordByCIdAndUIdForScratch(busCodeRecord, respInfo);
    }

}