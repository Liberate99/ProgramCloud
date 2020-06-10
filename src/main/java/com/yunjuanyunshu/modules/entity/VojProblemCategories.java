package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("voj_problem_categories")
public class VojProblemCategories extends Model<VojProblemCategories> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "problem_category_id", type = IdType.AUTO)
    private Integer problemCategoryId;
    @TableField("problem_category_slug")
    private String problemCategorySlug;
    @TableField("problem_category_name")
    private String problemCategoryName;
    @TableField("problem_category_parent_id")
    private Integer problemCategoryParentId;

    public VojProblemCategories() { }

    public VojProblemCategories(Integer problemCategoryId, String problemCategorySlug, String problemCategoryName, Integer problemCategoryParentId) {
        this.problemCategoryId = problemCategoryId;
        this.problemCategorySlug = problemCategorySlug;
        this.problemCategoryName = problemCategoryName;
        this.problemCategoryParentId = problemCategoryParentId;
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

    public Integer getProblemCategoryParentId() {
        return problemCategoryParentId;
    }

    public void setProblemCategoryParentId(Integer problemCategoryParentId) {
        this.problemCategoryParentId = problemCategoryParentId;
    }

    @Override
    protected Serializable pkVal() {
        return this.problemCategoryId;
    }

    @Override
    public String toString() {
        return "VojProblemCategories{" +
                "problemCategoryId=" + problemCategoryId +
                ", problemCategorySlug=" + problemCategorySlug +
                ", problemCategoryName=" + problemCategoryName +
                ", problemCategoryParentId=" + problemCategoryParentId +
                "}";
    }
}
