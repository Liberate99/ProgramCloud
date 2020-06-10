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
 * @author carl
 * @since 2017-11-17
 */
@TableName("bus_teacher_info")
public class BusTeacherInfo extends Model<BusTeacherInfo> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;
    /**
     * 教工号
     */
    @TableField("work_id")
    private String workId;
    /**
     * 学校编号
     */
    @TableField("college_id")
    private String collegeId;
    /**
     * 学院/系编号
     */
    @TableField("department_id")
    private String departmentId;
    /**
     * 专业
     */
    private String major;
    /**
     * 职称
     */
    @TableField("rank_id")
    private String rankId;
    /**
     * 个人简介
     */
    private String desc;
    @TableField("create_date")
    private Date createDate;
    @TableField("update_date")
    private Date updateDate;
    @TableField("del_flag")
    private String delFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusTeacherInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", workId=" + workId +
                ", collegeId=" + collegeId +
                ", departmentId=" + departmentId +
                ", major=" + major +
                ", rankId=" + rankId +
                ", desc=" + desc +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", delFlag=" + delFlag +
                "}";
    }
}
