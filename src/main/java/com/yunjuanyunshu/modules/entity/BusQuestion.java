package com.yunjuanyunshu.modules.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 问题表
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
@TableName("bus_question")
public class BusQuestion extends Model<BusQuestion> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
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
     * 奖金
     */
    private Integer bonus;
    /**
     * 问题标题
     */
    private String title;
    /**
     * 问题描述
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
     * 回答id
     */
    private Integer answer;
    /**
     * 状态
     */
    private String status;
    /**
     * 删除标记
     */
    @TableField("del_flag")
    private String delFlag;

    private String recommended;

    @TableField("question_type")
    private String questionType;

    private String referrer;

    private String codeId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
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

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
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

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
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
                ", bonus=" + bonus +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", chapter='" + chapter + '\'' +
                ", answer='" + answer + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
