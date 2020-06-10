package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusResource;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
public interface BusResourceService extends IService<BusResource> {
        void searchBusResourceByPage(Page<BusResource> page, HttpServletRequest request, BusResource busResource, RespInfo respInfo);
        }