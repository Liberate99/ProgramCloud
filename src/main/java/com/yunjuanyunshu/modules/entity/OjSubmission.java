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
 * @since 2018-02-12
 */
@TableName("oj_submission")
public class OjSubmission extends Model<OjSubmission> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("task_id")
    private String taskId;
    @TableField("problem_id")
    private Long problemId;
    @TableField("user_id")
    private String userId;
    @TableField("language_id")
    private Integer languageId;
    @TableField("submit_time")
    private Date submitTime;
    @TableField("execute_time")
    private Date executeTime;
    @TableField("used_time")
    private Integer usedTime;
    @TableField("used_memory")
    private Integer usedMemory;
    @TableField("judge_result")
    private String judgeResult;
    @TableField("judge_score")
    private Integer judgeScore;
    @TableField("judge_log")
    private String judgeLog;
    @TableField("submit_code")
    private String submitCode;
    @TableField("submit_input")
    private String submitInput;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Integer getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Integer usedTime) {
        this.usedTime = usedTime;
    }

    public Integer getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(Integer usedMemory) {
        this.usedMemory = usedMemory;
    }

    public String getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(String judgeResult) {
        this.judgeResult = judgeResult;
    }

    public Integer getJudgeScore() {
        return judgeScore;
    }

    public void setJudgeScore(Integer judgeScore) {
        this.judgeScore = judgeScore;
    }

    public String getJudgeLog() {
        return judgeLog;
    }

    public void setJudgeLog(String judgeLog) {
        this.judgeLog = judgeLog;
    }

    public String getSubmitCode() {
        return submitCode;
    }

    public void setSubmitCode(String submitCode) {
        this.submitCode = submitCode;
    }

    public String getSubmitInput() {
        return submitInput;
    }

    public void setSubmitInput(String submitInput) {
        this.submitInput = submitInput;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OjSubmission{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", problemId=" + problemId +
                ", userId=" + userId +
                ", languageId=" + languageId +
                ", submitTime=" + submitTime +
                ", executeTime=" + executeTime +
                ", usedTime=" + usedTime +
                ", usedMemory=" + usedMemory +
                ", judgeResult=" + judgeResult +
                ", judgeScore=" + judgeScore +
                ", judgeLog=" + judgeLog +
                ", submitCode=" + submitCode +
                ", submitInput=" + submitInput +
                "}";
    }
}
