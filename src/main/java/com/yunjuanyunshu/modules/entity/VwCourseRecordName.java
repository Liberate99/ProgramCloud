package com.yunjuanyunshu.modules.entity;

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
@TableName("vw_course_record_name")
public class VwCourseRecordName extends Model<VwCourseRecordName> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("course_id")
    private String courseId;
    /**
     * 标题
     */
    @TableField("course_name")
    private String courseName;
    @TableField("class_id")
    private String classId;
    /**
     * 班级名称
     */
    @TableField("class_name")
    private String className;
    @TableField("user_id")
    private String userId;
    /**
     * 姓名
     */
    @TableField("user_name")
    private String userName;
    private String lastest;
    @TableField("chapter_name")
    private String chapterName;
    /**
     * 完成度
     */
    private String completion;
    /**
     * 提问次数
     */
    @TableField("question_time")
    private  Integer questionTime;
    /**
     * 回答次数
     */
    @TableField("answer_time")
    private  Integer answerTime;
    /**
     * 被推荐次数
     */
    @TableField("recommendded_time")
    private  Integer recommenddedTime;
    /**
     * 被采纳次数
     */
    @TableField("accepted_time")
    private  Integer acceptedTime;

    public Integer getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(Integer questionTime) {
        this.questionTime = questionTime;
    }

    public Integer getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Integer answerTime) {
        this.answerTime = answerTime;
    }

    public Integer getRecommenddedTime() {
        return recommenddedTime;
    }

    public void setRecommenddedTime(Integer recommenddedTime) {
        this.recommenddedTime = recommenddedTime;
    }

    public Integer getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(Integer acceptedTime) {
        this.acceptedTime = acceptedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastest() {
        return lastest;
    }

    public void setLastest(String lastest) {
        this.lastest = lastest;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VwCourseRecordName{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", courseName=" + courseName +
                ", classId=" + classId +
                ", className=" + className +
                ", userId=" + userId +
                ", userName=" + userName +
                ", lastest=" + lastest +
                ", chapterName=" + chapterName +
                ", completion=" + completion +
                "}";
    }
}
