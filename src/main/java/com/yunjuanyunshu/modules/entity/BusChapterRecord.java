package com.yunjuanyunshu.modules.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@TableName("bus_chapter_record")
public class BusChapterRecord extends Model<BusChapterRecord> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("chapter_id")
    private String chapterId;
    @TableField("user_id")
    private String userId;
    /**
     * 完成度  1完成任务，0未完成
     */
    private Integer status;
    @TableField("create_time")
    private Date createTime;
    @TableField("create_by")
    private String createBy;
    @TableField("del_flag")
    private String delFlag;
    @TableField("finish_time")
    private Date finishTime;
    @TableField("course_id")
    private String courseId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusChapterRecord{" +
                "id=" + id +
                ", chapterId=" + chapterId +
                ", userId=" + userId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                ", delFlag=" + delFlag +
                ", finishTime=" + finishTime +
                ", courseId=" + courseId +
                "}";
    }
}
