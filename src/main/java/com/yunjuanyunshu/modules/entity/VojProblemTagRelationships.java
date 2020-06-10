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
@TableName("voj_problem_tag_relationships")
public class VojProblemTagRelationships extends Model<VojProblemTagRelationships> {

    private static final long serialVersionUID = 1L;

    @TableId("problem_id")
    private Long problemId;
    @TableField("problem_tag_id")
    private Long problemTagId;

    private transient String problemTagSlug;
    private transient String problemTagName;

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getProblemTagId() {
        return problemTagId;
    }

    public void setProblemTagId(Long problemTagId) {
        this.problemTagId = problemTagId;
    }

    public String getProblemTagSlug() {
        return problemTagSlug;
    }

    public void setProblemTagSlug(String problemTagSlug) {
        this.problemTagSlug = problemTagSlug;
    }

    public String getProblemTagName() {
        return problemTagName;
    }

    public void setProblemTagName(String problemTagName) {
        this.problemTagName = problemTagName;
    }

    @Override
    protected Serializable pkVal() {
        return this.problemId;
    }

    @Override
    public String toString() {
        return "VojProblemTagRelationships{" +
                "problemId=" + problemId +
                ", problemTagId=" + problemTagId +
                "}";
    }
}
