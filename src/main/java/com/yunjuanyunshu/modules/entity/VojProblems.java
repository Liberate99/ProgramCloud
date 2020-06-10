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
 * @since 2017-11-15
 */
@TableName("voj_problems")
public class VojProblems extends Model<VojProblems> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "problem_id", type = IdType.AUTO)
    private Long problemId;
    @TableField("problem_is_public")
    private Integer problemIsPublic;
    @TableField("problem_name")
    private String problemName;
    @TableField("problem_time_limit")
    private Integer problemTimeLimit;
    @TableField("problem_memory_limit")
    private Integer problemMemoryLimit;
    @TableField("problem_description")
    private String problemDescription;
    @TableField("problem_input_format")
    private String problemInputFormat;
    @TableField("problem_output_format")
    private String problemOutputFormat;
    @TableField("problem_sample_input")
    private String problemSampleInput;
    @TableField("problem_sample_output")
    private String problemSampleOutput;
    @TableField("problem_hint")
    private String problemHint;
    private transient String totalSubmission;
    private transient String acceptedSubmission;
    private transient String problemCategoryName;
    private transient String problemTagName;

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Integer getProblemIsPublic() {
        return problemIsPublic;
    }

    public void setProblemIsPublic(Integer problemIsPublic) {
        this.problemIsPublic = problemIsPublic;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public Integer getProblemTimeLimit() {
        return problemTimeLimit;
    }

    public void setProblemTimeLimit(Integer problemTimeLimit) {
        this.problemTimeLimit = problemTimeLimit;
    }

    public Integer getProblemMemoryLimit() {
        return problemMemoryLimit;
    }

    public void setProblemMemoryLimit(Integer problemMemoryLimit) {
        this.problemMemoryLimit = problemMemoryLimit;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getProblemInputFormat() {
        return problemInputFormat;
    }

    public void setProblemInputFormat(String problemInputFormat) {
        this.problemInputFormat = problemInputFormat;
    }

    public String getProblemOutputFormat() {
        return problemOutputFormat;
    }

    public void setProblemOutputFormat(String problemOutputFormat) {
        this.problemOutputFormat = problemOutputFormat;
    }

    public String getProblemSampleInput() {
        return problemSampleInput;
    }

    public void setProblemSampleInput(String problemSampleInput) {
        this.problemSampleInput = problemSampleInput;
    }

    public String getProblemSampleOutput() {
        return problemSampleOutput;
    }

    public void setProblemSampleOutput(String problemSampleOutput) {
        this.problemSampleOutput = problemSampleOutput;
    }

    public String getProblemHint() {
        return problemHint;
    }

    public void setProblemHint(String problemHint) {
        this.problemHint = problemHint;
    }

    public String getTotalSubmission() {
        return totalSubmission;
    }

    public void setTotalSubmission(String totalSubmission) {
        this.totalSubmission = totalSubmission;
    }

    public String getAcceptedSubmission() {
        return acceptedSubmission;
    }

    public void setAcceptedSubmission(String acceptedSubmission) {
        this.acceptedSubmission = acceptedSubmission;
    }

    public String getProblemCategoryName() {
        return problemCategoryName;
    }

    public void setProblemCategoryName(String problemCategoryName) {
        this.problemCategoryName = problemCategoryName;
    }

    public String getProblemTagName() {
        return problemTagName;
    }

    public void setProblemTagName(String problemTagName) {
        this.problemTagName = problemTagName;
    }

    @Override
    protected Serializable pkVal() {
        return this.problemId;
    }

    @Override
    public String toString() {
        return "VojProblems{" +
                "problemId=" + problemId +
                ", problemIsPublic=" + problemIsPublic +
                ", problemName=" + problemName +
                ", problemTimeLimit=" + problemTimeLimit +
                ", problemMemoryLimit=" + problemMemoryLimit +
                ", problemDescription=" + problemDescription +
                ", problemInputFormat=" + problemInputFormat +
                ", problemOutputFormat=" + problemOutputFormat +
                ", problemSampleInput=" + problemSampleInput +
                ", problemSampleOutput=" + problemSampleOutput +
                ", problemHint=" + problemHint +
                "}";
    }
}
