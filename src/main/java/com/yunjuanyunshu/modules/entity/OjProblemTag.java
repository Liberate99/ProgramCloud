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
 * @since 2018-02-14
 */
@TableName("oj_problem_tag")
public class OjProblemTag extends Model<OjProblemTag> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("tag_slug")
    private String tagSlug;
    @TableField("tag_name")
    private String tagName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return this.id;
    }

    @Override
    public String toString() {
        return "OjProblemTag{" +
                "id=" + id +
                ", tagSlug=" + tagSlug +
                ", tagName=" + tagName +
                "}";
    }
}
