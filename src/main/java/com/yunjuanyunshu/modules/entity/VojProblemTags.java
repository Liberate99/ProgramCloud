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
@TableName("voj_problem_tags")
public class VojProblemTags extends Model<VojProblemTags> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "problem_tag_id", type = IdType.AUTO)
    private Long problemTagId;
    @TableField("problem_tag_slug")
    private String problemTagSlug;
    @TableField("problem_tag_name")
    private String problemTagName;

    public VojProblemTags() { }

    public VojProblemTags(String problemTagSlug, String problemTagName) {
        this.problemTagSlug = problemTagSlug;
        this.problemTagName = problemTagName;
    }

    public VojProblemTags(Long problemTagId, String problemTagSlug, String problemTagName) {
        this.problemTagId = problemTagId;
        this.problemTagSlug = problemTagSlug;
        this.problemTagName = problemTagName;
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
        return this.problemTagId;
    }

    @Override
    public String toString() {
        return "VojProblemTags{" +
                "problemTagId=" + problemTagId +
                ", problemTagSlug=" + problemTagSlug +
                ", problemTagName=" + problemTagName +
                "}";
    }
}
