package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
@TableName("oj_problem_category_relation")
public class OjProblemCategoryRelation extends Model<OjProblemCategoryRelation> {

    private static final long serialVersionUID = 1L;

    @TableId("problem_id")
    private Long problemId;
    @TableField("category_id")
    private Integer categoryId;
    private transient String categorySlug;
    private transient String categoryName;


    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    protected Serializable pkVal() {
        return this.problemId;
    }

    @Override
    public String toString() {
        return "OjProblemCategoryRelation{" +
                "problemId=" + problemId +
                ", categoryId=" + categoryId +
                "}";
    }
}
