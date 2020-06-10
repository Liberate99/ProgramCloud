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
@TableName("oj_checkpoint")
public class OjCheckpoint extends Model<OjCheckpoint> {

    private static final long serialVersionUID = 1L;

    @TableId("problem_id")
    private Long problemId;
    @TableField("checkpoint_no")
    private Integer checkpointNo;
    @TableField("checkpoint_score")
    private Integer checkpointScore;
    @TableField("checkpoint_input")
    private String checkpointInput;
    @TableField("checkpoint_output")
    private String checkpointOutput;


    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Integer getCheckpointNo() {
        return checkpointNo;
    }

    public void setCheckpointNo(Integer checkpointNo) {
        this.checkpointNo = checkpointNo;
    }

    public Integer getCheckpointScore() {
        return checkpointScore;
    }

    public void setCheckpointScore(Integer checkpointScore) {
        this.checkpointScore = checkpointScore;
    }

    public String getCheckpointInput() {
        return checkpointInput;
    }

    public void setCheckpointInput(String checkpointInput) {
        this.checkpointInput = checkpointInput;
    }

    public String getCheckpointOutput() {
        return checkpointOutput;
    }

    public void setCheckpointOutput(String checkpointOutput) {
        this.checkpointOutput = checkpointOutput;
    }

    @Override
    protected Serializable pkVal() {
        return this.problemId;
    }

    @Override
    public String toString() {
        return "OjCheckpoint{" +
                "problemId=" + problemId +
                ", checkpointNo=" + checkpointNo +
                ", checkpointScore=" + checkpointScore +
                ", checkpointInput=" + checkpointInput +
                ", checkpointOutput=" + checkpointOutput +
                "}";
    }
}
