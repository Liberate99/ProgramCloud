package com.yunjuanyunshu.modules.mapper;

import com.yunjuanyunshu.modules.entity.Log;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
public interface LogDao extends BaseMapper<Log> {

    public int insertLog(Log log);
}