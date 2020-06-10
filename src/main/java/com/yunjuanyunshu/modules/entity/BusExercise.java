package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *     练习题表
 * </p>
 *
 * @auther DJ
 * @since 2018-8-3
 */
@TableName("bus_exercise")
public class BusExercise extends Model<BusExercise> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 父节点id
     */
    private String parentId;
    /**
     * 所属知识点
     */
    private String knowledgeTag;
    /**
     * 类型
     */
    private String type;
    /**
     * 课程id
     */
    @TableField("course_id")
    private String courseId;
    /**
     * 练习题标题
     */
    private String title;
    /**
     * 练习题题目
     */
    private String description;
    /**
     * 代码
     */
    private String code;
    /**
     * 提问者
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 提问时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 最新回复时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 章节id
     */
    private String chapter;
    /**
     * 状态
     */
    private String status;
    /**
     * 删除标记
     */
    @TableField("del_flag")
    private String delFlag;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKnowledgeTag() {
        return knowledgeTag;
    }

    public void setKnowledgeTag(String knowledgeTag) {
        this.knowledgeTag = knowledgeTag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCodeId() {
        return code;
    }

    public void setCodeId(String codeId) {
        this.code = codeId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusQuestion{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", courseId='" + courseId + '\'' +
                ", knowledgeTag='" + knowledgeTag + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", chapter='" + chapter + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
