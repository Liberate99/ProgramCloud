package com.yunjuanyunshu.modules.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 回答表
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
@TableName("bus_answer")
public class BusAnswer extends Model<BusAnswer> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 问题原因
     */
    private String reason;
    /**
     * 代码
     */
    private String code;
    /**
     * 回答者
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 回答时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 回答所属问题id
     */
    @TableField("question_id")
    private String questionId;
    /**
     * 回答支持数
     */
    @TableField("support_num")
    private Integer supportNum;

    /**
     * 被采纳
     */
    private String accepted;

    /**
     * 被推荐
     */
    private String recommended;

    /**
     * 推荐人
     */
    private String referrer;

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Integer getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Integer supportNum) {
        this.supportNum = supportNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusAnswer{" +
                "id='" + id + '\'' +
                ", reason='" + reason + '\'' +
                ", code='" + code + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", questionId='" + questionId + '\'' +
                ", supportNum=" + supportNum +
                '}';
    }
}
