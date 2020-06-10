package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusProduct;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-07-27
 */
public interface BusProductService extends IService<BusProduct> {
        public void getPro(BusProduct busProduct,RespInfo respInfo);
        void getBusProductByPage(Page<BusProduct> page, RespInfo respInfo);
        }