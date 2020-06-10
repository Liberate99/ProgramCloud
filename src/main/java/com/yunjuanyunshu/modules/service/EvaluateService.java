package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 回答表 服务类
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
public interface EvaluateService  {

        /**
         * 获取章节列表
         * @param busClass

         * @param respInfo
         */
        void getEvaluateInfoByClassId(BusClass busClass, RespInfo respInfo);

}