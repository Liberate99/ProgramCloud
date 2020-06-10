package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

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
@TableName("voj_submissions")
public class VojSubmissions extends Model<VojSubmissions> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "submission_id", type = IdType.AUTO)
    private Long submissionId;
    @TableField("problem_id")
    private Long problemId;
    private Long uid;
    @TableField("language_id")
    private Integer languageId;
    @TableField("submission_submit_time")
    private Date submissionSubmitTime;
    @TableField("submission_execute_time")
    private Date submissionExecuteTime;
    @TableField("submission_used_time")
    private Integer submissionUsedTime;
    @TableField("submission_used_memory")
    private Integer submissionUsedMemory;
    @TableField("submission_judge_result")
    private String submissionJudgeResult;
    @TableField("submission_judge_score")
    private Integer submissionJudgeScore;
    @TableField("submission_judge_log")
    private String submissionJudgeLog;
    @TableField("submission_code")
    private String submissionCode;


    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Date getSubmissionSubmitTime() {
        return submissionSubmitTime;
    }

    public void setSubmissionSubmitTime(Date submissionSubmitTime) {
        this.submissionSubmitTime = submissionSubmitTime;
    }

    public Date getSubmissionExecuteTime() {
        return submissionExecuteTime;
    }

    public void setSubmissionExecuteTime(Date submissionExecuteTime) {
        this.submissionExecuteTime = submissionExecuteTime;
    }

    public Integer getSubmissionUsedTime() {
        return submissionUsedTime;
    }

    public void setSubmissionUsedTime(Integer submissionUsedTime) {
        this.submissionUsedTime = submissionUsedTime;
    }

    public Integer getSubmissionUsedMemory() {
        return submissionUsedMemory;
    }

    public void setSubmissionUsedMemory(Integer submissionUsedMemory) {
        this.submissionUsedMemory = submissionUsedMemory;
    }

    public String getSubmissionJudgeResult() {
        return submissionJudgeResult;
    }

    public void setSubmissionJudgeResult(String submissionJudgeResult) {
        this.submissionJudgeResult = submissionJudgeResult;
    }

    public Integer getSubmissionJudgeScore() {
        return submissionJudgeScore;
    }

    public void setSubmissionJudgeScore(Integer submissionJudgeScore) {
        this.submissionJudgeScore = submissionJudgeScore;
    }

    public String getSubmissionJudgeLog() {
        return submissionJudgeLog;
    }

    public void setSubmissionJudgeLog(String submissionJudgeLog) {
        this.submissionJudgeLog = submissionJudgeLog;
    }

    public String getSubmissionCode() {
        return submissionCode;
    }

    public void setSubmissionCode(String submissionCode) {
        this.submissionCode = submissionCode;
    }

    @Override
    protected Serializable pkVal() {
        return this.submissionId;
    }

    @Override
    public String toString() {
        return "VojSubmissions{" +
                "submissionId=" + submissionId +
                ", problemId=" + problemId +
                ", uid=" + uid +
                ", languageId=" + languageId +
                ", submissionSubmitTime=" + submissionSubmitTime +
                ", submissionExecuteTime=" + submissionExecuteTime +
                ", submissionUsedTime=" + submissionUsedTime +
                ", submissionUsedMemory=" + submissionUsedMemory +
                ", submissionJudgeResult=" + submissionJudgeResult +
                ", submissionJudgeScore=" + submissionJudgeScore +
                ", submissionJudgeLog=" + submissionJudgeLog +
                ", submissionCode=" + submissionCode +
                "}";
    }
}
