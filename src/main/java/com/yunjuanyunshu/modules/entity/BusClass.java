package com.yunjuanyunshu.modules.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author qch
 * @since 2017-10-27
 */
@TableName("bus_class")
public class BusClass extends Model<BusClass> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 班级名称
     */
    private String name;
    /**
     * 课程id
     */
    @TableField("course_id")
    private String courseId;
    /**
     * 机构id
     */
    @TableField("organization_id")
    private String organizationId;
    /**
     * 学年
     */
    @TableField("school_year")
    private String schoolYear;
    /**
     * 学期
     */
    private String semester;
    /**
     * 邀请码
     */
    @TableField("invitation_code")
    private String invitationCode;
    /**
     * 开班时间
     */
    @TableField("begin_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    /**
     * 结束时间
     */
    @TableField("finish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishDate;
    /**
     * 开班状态:0:未开始;1:开班中;2:结束
     */
    private String status;
    @TableField("create_by")
    private String createBy;
    @TableField("create_date")
    private Date createDate;
    @TableField("update_by")
    private String updateBy;
    @TableField("update_date")
    private Date updateDate;
    @TableField("del_flag")
    private String delFlag;

    private transient String courseName;
    private transient String schoolYearName;
    private transient String semesterName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchoolYearName() {
        return schoolYearName;
    }

    public void setSchoolYearName(String schoolYearName) {
        this.schoolYearName = schoolYearName;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusClass{" +
                "id=" + id +
                ", name=" + name +
                ", courseId=" + courseId +
                ", organizationId=" + organizationId +
                ", schoolYear=" + schoolYear +
                ", semester=" + semester +
                ", invitationCode=" + invitationCode +
                ", beginDate=" + beginDate +
                ", finishDate=" + finishDate +
                ", status=" + status +
                ", createBy=" + createBy +
                ", createDate=" + createDate +
                ", updateBy=" + updateBy +
                ", updateDate=" + updateDate +
                ", delFlag=" + delFlag +
                "}";
    }
}
