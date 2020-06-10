package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusScratchFile;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.aop.annotation.RequestParameterCanNullAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * Scratch作品表 服务类
 * </p>
 *
 * @author carl
 * @since 2017-12-10
 */
public interface BusScratchFileService extends IService<BusScratchFile> {

    void getScratchFileListByUser(String userId, RespInfo respInfo);

    void searchScratchFileByUser(String userId, String likeName, RespInfo respInfo);

    void getScratchFileDetail(BusScratchFile busScratchFile, RespInfo respInfo);
    void saveScratchFile(BusScratchFile busScratchFile, RespInfo respInfo);
    void getBusScratchFileByPage(Page<BusScratchFile> page, String type, String key, RespInfo respInfo);

}