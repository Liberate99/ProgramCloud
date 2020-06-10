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
@TableName("oj_problem")
public class OjProblem extends Model<OjProblem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("has_public")
    private Integer hasPublic;
    @TableField("time_limit")
    private Integer timeLimit;
    @TableField("memory_limit")
    private Integer memoryLimit;
    private String name;
    private String description;
    @TableField("has_input")
    private Integer hasInput;
    @TableField("input_format")
    private String inputFormat;
    @TableField("sample_input")
    private String sampleInput;
    @TableField("has_output")
    private Integer hasOutput;
    @TableField("output_format")
    private String outputFormat;
    @TableField("sample_output")
    private String sampleOutput;
    @TableField("has_checkpoint")
    private Integer hasCheckpoint;
    @TableField("has_hint")
    private Integer hasHint;
    private String hint;
    @TableField("create_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_by")
    private String updateBy;
    @TableField("update_time")
    private Date updateTime;
    @TableField("del_flag")
    private Integer delFlag;
    private transient String totalSubmission;
    private transient String acceptedSubmission;
    private transient String categoryName;
    private transient String tagName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHasPublic() {
        return hasPublic;
    }

    public void setHasPublic(Integer hasPublic) {
        this.hasPublic = hasPublic;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Integer memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHasInput() {
        return hasInput;
    }

    public void setHasInput(Integer hasInput) {
        this.hasInput = hasInput;
    }

    public String getInputFormat() {
        return inputFormat;
    }

    public void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public Integer getHasOutput() {
        return hasOutput;
    }

    public void setHasOutput(Integer hasOutput) {
        this.hasOutput = hasOutput;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getSampleOutput() {
        return sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }

    public Integer getHasCheckpoint() {
        return hasCheckpoint;
    }

    public void setHasCheckpoint(Integer hasCheckpoint) {
        this.hasCheckpoint = hasCheckpoint;
    }

    public Integer getHasHint() {
        return hasHint;
    }

    public void setHasHint(Integer hasHint) {
        this.hasHint = hasHint;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
        return "OjProblem{" +
                "id=" + id +
                ", hasPublic=" + hasPublic +
                ", timeLimit=" + timeLimit +
                ", memoryLimit=" + memoryLimit +
                ", name=" + name +
                ", description=" + description +
                ", hasInput=" + hasInput +
                ", inputFormat=" + inputFormat +
                ", sampleInput=" + sampleInput +
                ", hasOutput=" + hasOutput +
                ", outputFormat=" + outputFormat +
                ", sampleOutput=" + sampleOutput +
                ", hasCheckpoint=" + hasCheckpoint +
                ", hasHint=" + hasHint +
                ", hint=" + hint +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                "}";
    }
}
