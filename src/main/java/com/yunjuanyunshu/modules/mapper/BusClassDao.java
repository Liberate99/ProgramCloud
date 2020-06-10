package com.yunjuanyunshu.modules.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yunjuanyunshu.modules.entity.BusClass;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author qch
 * @since 2017-10-27
 */
public interface BusClassDao extends BaseMapper<BusClass> {

    /**
     * 根据分页查找班级列表包含课程名称,学年名称和学期名称
     * @param page
     * @return
     */
    List<BusClass> selectBusClassesWithName(Page<BusClass> page,@Param("ew") Wrapper<BusClass> wrapper);
}