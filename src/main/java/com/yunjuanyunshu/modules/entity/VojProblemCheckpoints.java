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
 * @since 2017-11-15
 */
@TableName("voj_problem_checkpoints")
public class VojProblemCheckpoints extends Model<VojProblemCheckpoints> {

    private static final long serialVersionUID = 1L;

    @TableId("problem_id")
    private Long problemId;
    @TableField("checkpoint_id")
    private Integer checkpointId;
    @TableField("checkpoint_exactly_match")
    private Integer checkpointExactlyMatch;
    @TableField("checkpoint_score")
    private Integer checkpointScore;
    @TableField("checkpoint_input")
    private String checkpointInput;
    @TableField("checkpoint_output")
    private String checkpointOutput;

    public VojProblemCheckpoints() {}

    public VojProblemCheckpoints(Long problemId, Integer checkpointId, Integer checkpointExactlyMatch,
                                 Integer checkpointScore, String checkpointInput, String checkpointOutput) {
        this.problemId = problemId;
        this.checkpointId = checkpointId;
        this.checkpointExactlyMatch = checkpointExactlyMatch;
        this.checkpointScore = checkpointScore;
        this.checkpointInput = checkpointInput;
        this.checkpointOutput = checkpointOutput;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Integer getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(Integer checkpointId) {
        this.checkpointId = checkpointId;
    }

    public Integer getCheckpointExactlyMatch() {
        return checkpointExactlyMatch;
    }

    public void setCheckpointExactlyMatch(Integer checkpointExactlyMatch) {
        this.checkpointExactlyMatch = checkpointExactlyMatch;
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
        return "VojProblemCheckpoints{" +
                "problemId=" + problemId +
                ", checkpointId=" + checkpointId +
                ", checkpointExactlyMatch=" + checkpointExactlyMatch +
                ", checkpointScore=" + checkpointScore +
                ", checkpointInput=" + checkpointInput +
                ", checkpointOutput=" + checkpointOutput +
                "}";
    }
}
