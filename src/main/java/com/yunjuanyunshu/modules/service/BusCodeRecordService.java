package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.BusCodeRecord;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.BusScratchFile;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xjz
 * @since 2017-07-18
 */
public interface BusCodeRecordService extends IService<BusCodeRecord> {
    public void execCplus(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue, RespInfo respInfo);
    public void execJava(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue, RespInfo respInfo);
    public void execPy(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue, RespInfo respInfo);
    void execHtml(HttpServletRequest request, BusCodeRecord busCodeRecord, RespInfo respInfo);
    void getRecordByCIdAndUId(BusCodeRecord busCodeRecord,RespInfo respInfo);
    void getRecordByCIdAndUIdForScratch(BusCodeRecord busCodeRecord,RespInfo respInfo);
    void insertScratch(BusScratchFile busScratchFile, BusCodeRecord busCodeRecord, RespInfo respInfo);
}