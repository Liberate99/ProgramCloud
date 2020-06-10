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
@TableName("voj_judge_results")
public class VojJudgeResults extends Model<VojJudgeResults> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "judge_result_id", type = IdType.AUTO)
    private Integer judgeResultId;
    @TableField("judge_result_slug")
    private String judgeResultSlug;
    @TableField("judge_result_name")
    private String judgeResultName;


    public Integer getJudgeResultId() {
        return judgeResultId;
    }

    public void setJudgeResultId(Integer judgeResultId) {
        this.judgeResultId = judgeResultId;
    }

    public String getJudgeResultSlug() {
        return judgeResultSlug;
    }

    public void setJudgeResultSlug(String judgeResultSlug) {
        this.judgeResultSlug = judgeResultSlug;
    }

    public String getJudgeResultName() {
        return judgeResultName;
    }

    public void setJudgeResultName(String judgeResultName) {
        this.judgeResultName = judgeResultName;
    }

    @Override
    protected Serializable pkVal() {
        return this.judgeResultId;
    }

    @Override
    public String toString() {
        return "VojJudgeResults{" +
                "judgeResultId=" + judgeResultId +
                ", judgeResultSlug=" + judgeResultSlug +
                ", judgeResultName=" + judgeResultName +
                "}";
    }
}
