package com.yunjuanyunshu.modules.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author carl
 * @since 2017-11-02
 */
@TableName("vw_class_info")
public class VwClassInfo extends Model<VwClassInfo> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 班级名称
     */
    @TableField("class_name")
    private String className;
    /**
     * 课程id
     */
    @TableField("course_id")
    private String courseId;
    /**
     * 标题
     */
    @TableField("course_name")
    private String courseName;
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
    private Date beginDate;
    /**
     * 结束时间
     */
    @TableField("finish_date")
    private Date finishDate;
    /**
     * 开班状态:0:未开始;1:开班中;2:结束
     */
    private String status;
    @TableField("create_by")
    private String createBy;
    /**
     * 姓名
     */
    @TableField("create_name")
    private String createName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VwClassInfo{" +
                "id=" + id +
                ", className=" + className +
                ", courseId=" + courseId +
                ", courseName=" + courseName +
                ", schoolYear=" + schoolYear +
                ", semester=" + semester +
                ", invitationCode=" + invitationCode +
                ", beginDate=" + beginDate +
                ", finishDate=" + finishDate +
                ", status=" + status +
                ", createBy=" + createBy +
                ", createName=" + createName +
                "}";
    }
}
