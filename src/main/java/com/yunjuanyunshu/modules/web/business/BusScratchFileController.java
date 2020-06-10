package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusScratchFile;
import com.yunjuanyunshu.modules.entity.BusScratchFileVal;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusScratchFileService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.RequestParameterCanNullAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.util.Date;


/**
 * <p>
 * Scratch作品表 前端控制器
 * </p>
 *
 * @author carl
 * @since 2017-12-07
 */
@Component
@ClassAnnot("business.busScratchFile")
public class BusScratchFileController {
    private static BusScratchFileService busScratchFileService;

    /**
     * 静态注入service
     *
     * @param busScratchFileService
     */
    @Autowired
    public BusScratchFileController(BusScratchFileService busScratchFileService) {
        BusScratchFileController.busScratchFileService = busScratchFileService;
    }

    /**
     * Delete boolean.
     *
     * @param busScratchFile the busScratchFile
     * @return the boolean
     */
    @MethodAnnot
    public static boolean delete(BusScratchFile busScratchFile) {
        return busScratchFileService.deleteById(busScratchFile);
    }

    /**
     * Insert or update boolean.
     *
     * @param busScratchFile the busScratchFile
     * @return the boolean
     */
    @MethodAnnot
    public static void insertOrUpdate(BusScratchFile busScratchFile,RespInfo respInfo)  {
        busScratchFileService.saveScratchFile(busScratchFile,respInfo);
    }

    /**
     * Get busScratchFile.
     *
     * @param busScratchFile the busScratchFile
     * @return the busScratchFile
     */
    @MethodAnnot
    public static BusScratchFile get(BusScratchFile busScratchFile) {
        return busScratchFileService.selectById(busScratchFile);
    }
    @MethodAnnot
    public static BusScratchFile getName(BusScratchFile busScratchFile) {
        return busScratchFileService.selectOne(new EntityWrapper<BusScratchFile>().setSqlSelect("id,name").
                eq("id",busScratchFile.getId()));
    }
    /**
     * Gets busScratchFile by page.
     *
     * @return the busScratchFile by page
     */
    @MethodAnnot
    public static void getBusScratchFileByPage(Page<BusScratchFile> page,String type,String key,RespInfo respInfo) {
        busScratchFileService.getBusScratchFileByPage( page, type, key ,respInfo);
//        return busScratchFileService.selectPage(page,
//                new EntityWrapper<BusScratchFile>().orderBy(page.getOrderByField(),
//                        page.isAsc()));
    }
    /**
     * Gets busScratchFile by page.
     *
     * @return the busScratchFile by page
     */
    @MethodAnnot
    public static Page<BusScratchFile> getBusScratchProjectByPage(Page<BusScratchFile> page) {
        return busScratchFileService.selectPage(page,
                new EntityWrapper<BusScratchFile>().
                        setSqlSelect("id,cover,name,create_by,create_date,hits,scan,version,status").
                        eq("type","1").orderBy(page.getOrderByField(),
                        page.isAsc()));
    }

    /**
     * 获取当前用户的scratch作品列表
     *
     * @param userId   用户唯一标识符
     * @param respInfo
     */
    @MethodAnnot
    public static void getScratchFileListByUser(String userId, RespInfo respInfo) {
        busScratchFileService.getScratchFileListByUser(userId, respInfo);
    }

    /**
     * 通过作品名称搜索用户作品列表
     *
     * @param userId
     * @param likeName
     * @param respInfo
     */
    @MethodAnnot
    public static void searchScratchFileByUser(String userId, String likeName, RespInfo respInfo) {
        busScratchFileService.searchScratchFileByUser(userId, likeName, respInfo);
    }

    /**
     * 获取作品详细信息
     *
     * @param busScratchFile
     * @param respInfo
     */
    @MethodAnnot
    public static void getScratchFileDetail(BusScratchFile busScratchFile, RespInfo respInfo) {
        busScratchFileService.getScratchFileDetail(busScratchFile, respInfo);
    }

}