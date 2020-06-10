package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusScratchFile;
import com.yunjuanyunshu.modules.entity.BusScratchFileVal;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.mapper.BusScratchFileDao;
import com.yunjuanyunshu.modules.service.BusScratchFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.aop.annotation.ParameterCanNull;
import com.yunjuanyunshu.yunfd.aop.annotation.RequestParameterCanNullAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Scratch作品表 服务实现类
 * </p>
 *
 * @author carl
 * @since 2017-12-07
 */
@Service
public class BusScratchFileServiceImpl extends ServiceImpl<BusScratchFileDao, BusScratchFile> implements BusScratchFileService {


    /**
     * 获取当前用户的scratch文件列表
     *
     * @param userId   用户唯一标识符
     * @param respInfo
     */
    @Override
    public void getScratchFileListByUser(String userId, RespInfo respInfo) {
        List<BusScratchFile> scratchFiles = super.selectList(new EntityWrapper<BusScratchFile>()
                .setSqlSelect("id, name, create_by AS createBy, cover")
                .eq("create_by", userId));
        respInfo.setValue(scratchFiles);
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setMsg("获取作品列表成功");
    }

    /**
     * 通过作品名称搜索用户作品列表
     * @param userId
     * @param likeName
     * @param respInfo
     */
    @Override
    public void searchScratchFileByUser(String userId, String likeName, RespInfo respInfo) {
        List<BusScratchFile> scratchFiles = super.selectList(new EntityWrapper<BusScratchFile>()
                .setSqlSelect("id, name, create_by AS createBy, cover")
                .like("name", likeName)
                .eq("create_by", userId));
        respInfo.setValue(scratchFiles);
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setMsg("获取作品列表成功");
    }

    /**
     * 获取作品详细信息
     *
     * @param busScratchFile
     * @param respInfo
     */
    @Override
    public void getScratchFileDetail(BusScratchFile busScratchFile, RespInfo respInfo) {
        BusScratchFile bsfRes = super.selectById(busScratchFile);
        if (bsfRes == null) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("该作品不存在");
            return;
        }

        String content = new String(Base64Utils.decodeFromString(bsfRes.getContent()));
        bsfRes.setContent(content);
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setMsg("获取作品详情成功");
        respInfo.setValue(bsfRes);
    }

    @Override
    public void saveScratchFile(BusScratchFile busScratchFile, RespInfo respInfo) {
            try{
                if (busScratchFile.getId() == null) {
                    busScratchFile.setCreateDate(new Date());
                    busScratchFile.setContent(Base64Utils.encodeToString(busScratchFile.getContent().getBytes()));
                    respInfo.setValue(super.insert(busScratchFile));
                    return;
                }
                busScratchFile.setUpdateDate(new Date());
                busScratchFile.setContent(Base64Utils.encodeToString(busScratchFile.getContent().getBytes()));
                super.insertOrUpdate(busScratchFile);
            }catch (Exception e){
                respInfo.setValue(e.getMessage());
                respInfo.setCode(-1);
                e.printStackTrace();
            }


    }

    @Override
    public void getBusScratchFileByPage(Page<BusScratchFile> page, @RequestParameterCanNullAnnot(true) String type, @RequestParameterCanNullAnnot(true) String key, RespInfo respInfo) {
        try{
           EntityWrapper ew = new EntityWrapper<BusScratchFile>();
            if(!StringUtils.isEmpty(key)){
                ew.like("name",key);
            }
            if(!StringUtils.isEmpty(type)){
                ew.eq("type",type);
            }
            ew.setSqlSelect("id,cover,name,create_by as createBy,update_by as updateBy,update_date as updateDate,create_date as createDate,hits,scan,version,status").orderBy(page.getOrderByField(),
                   page.isAsc());
              super.selectPage(page,ew);
              respInfo.setValue(page);
        }catch (Exception e){
            respInfo.setValue(e.getMessage());
            respInfo.setCode(-1);
            e.printStackTrace();
        }
    }
}