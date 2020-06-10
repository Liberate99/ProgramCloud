package com.yunjuanyunshu.modules.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yunjuanyunshu.modules.entity.OjProblem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
public interface OjProblemDao extends BaseMapper<OjProblem> {

    /**
     * 添加试题
     * @param ojProblem
     * @return
     */
    int addProblem(OjProblem ojProblem);

    /**
     * 更新试题
     * @param ojProblem
     * @return
     */
    int updateProblem(OjProblem ojProblem);

    /**
     * 根据分页查找试题集包含全部提交数和通过提交数
     * @param page
     * @return
     */
    List<OjProblem> selectProblemWithTotalAndAcList(Pagination page,@Param("categoryId") Integer categoryId, @Param("keyword") String keyword,@Param("userId") String userId,@Param("publicType") Integer publicType);

    /**
     * 根据试题名称关键字或者试题分类查找试题
     * @param page
     * @param categoryId
     * @param keyword
     * @return
     */
    List<OjProblem> selectProblemByCategoryIdAndName(Pagination page, @Param("categoryId") Integer categoryId, @Param("keyword") String keyword,@Param("userId") String userId);
}