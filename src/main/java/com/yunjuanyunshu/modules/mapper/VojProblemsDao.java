package com.yunjuanyunshu.modules.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yunjuanyunshu.modules.entity.VojProblems;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author carl
 * @since 2017-11-15
 */
public interface VojProblemsDao extends BaseMapper<VojProblems> {

    /**
     * 根据分页查找试题集包含全部提交数和通过提交数
     * @param page
     * @return
     */
    List<VojProblems> selectVojProblemsWithTotalAndAcList(Pagination page);


    /**
     * 创建一个新的试题对象.
     * @param vojProblems 试题对象
     * @return
     */
    int createProblem(VojProblems vojProblems);
}