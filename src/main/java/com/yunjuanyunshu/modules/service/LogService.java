package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.Log;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
public interface LogService extends IService<Log> {


    public int insertLog(Log log);
}
