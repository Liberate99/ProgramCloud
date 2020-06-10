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
 * @since 2017-11-15
 */
@TableName("voj_problem_category_relationships")
public class VojProblemCategoryRelationships extends Model<VojProblemCategoryRelationships> {

    private static final long serialVersionUID = 1L;

    @TableId("problem_id")
    private Long problemId;
    @TableField("problem_category_id")
    private Integer problemCategoryId;

    /**
     * 试题分类的别名.
     */
    private transient String problemCategorySlug;

    /**
     * 试题分类的名称.
     */
    private transient String problemCategoryName;

    public VojProblemCategoryRelationships() {}

    public VojProblemCategoryRelationships(Long problemId, Integer problemCategoryId) {
        this.problemId = problemId;
        this.problemCategoryId = problemCategoryId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Integer getProblemCategoryId() {
        return problemCategoryId;
    }

    public void setProblemCategoryId(Integer problemCategoryId) {
        this.problemCategoryId = problemCategoryId;
    }

    public String getProblemCategorySlug() {
        return problemCategorySlug;
    }

    public void setProblemCategorySlug(String problemCategorySlug) {
        this.problemCategorySlug = problemCategorySlug;
    }

    public String getProblemCategoryName() {
        return problemCategoryName;
    }

    public void setProblemCategoryName(String problemCategoryName) {
        this.problemCategoryName = problemCategoryName;
    }

    @Override
    protected Serializable pkVal() {
        return this.problemId;
    }

    @Override
    public String toString() {
        return "VojProblemCategoryRelationships{" +
                "problemId=" + problemId +
                ", problemCategoryId=" + problemCategoryId +
                "}";
    }
}
