package com.yunjuanyunshu.modules.service.impl;

import com.yunjuanyunshu.modules.entity.Log;
import com.yunjuanyunshu.modules.mapper.LogDao;
import com.yunjuanyunshu.modules.service.LogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogDao, Log> implements LogService {

    @Override
    public int insertLog(Log log) {
        return baseMapper.insertLog(log);
    }
}
