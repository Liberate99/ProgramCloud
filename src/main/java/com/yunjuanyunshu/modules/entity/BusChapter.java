package com.yunjuanyunshu.modules.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@TableName("bus_chapter")
public class BusChapter extends Model<BusChapter> {

    private static final long serialVersionUID = 1L;

    private String id;
    private transient String oldId;
    @TableField("parent_id")
    private String parentId;
    private transient String oldParentId;
    @TableField("parent_ids")
    private String parentIds;
    private String title;
    private BigDecimal sort;
    private String des;
    @TableField("resource_id")
    private String resourceId;
    @TableField("code_id")
    private String codeId;
    @TableField("course_id")
    private String courseId;
    private Integer classtime;
    private Integer score;
    @TableField("create_time")
    private Date createTime;
    @TableField("create_by")
    private String createBy;
    @TableField("del_flag")
    private String delFlag;
    @TableField("has_public")
    private Integer hasPublic;
    private String type;

    public Integer getHasPublic() {
        return hasPublic;
    }

    public void setHasPublic(Integer hasPublic) {
        this.hasPublic = hasPublic;
    }

    /**
     * 临时变量是否完成
     */
    private transient Integer hasFinished;
    /**
     * 临时变量资源url
     */
    private transient String resourceUrl;
    /**
     * 临时变量完成时间
     */
    private transient String finishDate;

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public String getOldParentId() {
        return oldParentId;
    }

    public void setOldParentId(String oldParentId) {
        this.oldParentId = oldParentId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }


    public Integer getHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(Integer hasFinished) {
        this.hasFinished = hasFinished;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public Integer getClasstime() {
        return classtime;
    }

    public void setClasstime(Integer classtime) {
        this.classtime = classtime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusChapter{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", parentIds=" + parentIds +
                ", title=" + title +
                ", sort=" + sort +
                ", des=" + des +
                ", resourceId=" + resourceId +
                ", codeId=" + codeId +
                ", classtime=" + classtime +
                ", score=" + score +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                ", delFlag=" + delFlag +
                ", type=" + type +
                "}";
    }
}
