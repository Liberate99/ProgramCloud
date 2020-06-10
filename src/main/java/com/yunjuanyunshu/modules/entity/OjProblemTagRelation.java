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
@TableName("oj_problem_tag_relation")
public class OjProblemTagRelation extends Model<OjProblemTagRelation> {

    private static final long serialVersionUID = 1L;

    @TableId("problem_id")
    private Long problemId;
    @TableField("tag_id")
    private Long tagId;

    private transient String tagSlug;
    private transient String tagName;

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagSlug() {
        return tagSlug;
    }

    public void setTagSlug(String tagSlug) {
        this.tagSlug = tagSlug;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    protected Serializable pkVal() {
        return this.problemId;
    }

    @Override
    public String toString() {
        return "OjProblemTagRelation{" +
                "problemId=" + problemId +
                ", tagId=" + tagId +
                "}";
    }
}
