package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunjuanyunshu.modules.entity.BusScratchResource;
import com.yunjuanyunshu.modules.mapper.BusScratchResourceDao;
import com.yunjuanyunshu.modules.service.BusScratchResourceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author carl
 * @since 2017-12-10
 */
@Service
public class BusScratchResourceServiceImpl extends ServiceImpl<BusScratchResourceDao, BusScratchResource> implements BusScratchResourceService {

    /**
     * 获取资源素材
     *
     * @param type
     * @param respInfo
     */
    @Override
    public void getResourceListByType(String type, RespInfo respInfo) {
        List<BusScratchResource> busScratchResources = super.selectList(new EntityWrapper<BusScratchResource>().eq("type", type));
        respInfo.setMsg("获取资源列表成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setValue(busScratchResources);
    }
}