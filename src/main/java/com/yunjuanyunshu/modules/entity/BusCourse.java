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
 * @since 2017-07-07
 */
@TableName("bus_course")
public class BusCourse extends Model<BusCourse> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String desc;
    /**
     * 课程概览
     */
    private String overview;
    /**
     * 章节
     */
//            @TableField("chapter_id")
//        private String chapterId;
    /**
     * 分类
     */
    private String classify;
    /**
     * 课时
     */
    private Integer classtime;
    /**
     * 学分
     */
    private Integer score;
    @TableField("create_time")
    private Date createTime;
    @TableField("create_by")
    private String createBy;
    /**
     * 目前暂时作为课程发布状态
     */
    @TableField("del_flag")
    private String delFlag;
    /**
     * 公私有状态
     */
    @TableField("has_public")
    private Integer hasPublic;
    /**
     * 图片
     */
    private String pic;
    /**
     * 浏览人数
     */
    private String view;
    /**
     * 加入人数
     */
    private String focus;
    private transient String username;
    private transient String classifyName;
    private transient String delFlagName;
    private transient String updateDate;
    /**
     * 模版课程Id
     */
    private transient String templateCourseId;

    public String getTemplateCourseId() {
        return templateCourseId;
    }

    public void setTemplateCourseId(String templateCourseId) {
        this.templateCourseId = templateCourseId;
    }

    public Integer getHasPublic() {
        return hasPublic;
    }

    public void setHasPublic(Integer hasPublic) {
        this.hasPublic = hasPublic;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getDelFlagName() {
        return delFlagName;
    }

    public void setDelFlagName(String delFlagName) {
        this.delFlagName = delFlagName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
//
//    public String getChapterId() {
//            return chapterId;
//            }
//
//                public void setChapterId(String chapterId) {
//                    this.chapterId = chapterId;
//                    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Integer getClasstime() {
        return classtime;
    }

    public void setClasstime(Integer classtime) {
        this.classtime = classtime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusCourse{" +
                "id=" + id +
                ", title=" + title +
                ", desc=" + desc +
                ", overview=" + overview +
                ", chapterId=" +
//                chapterId +
                ", classify=" + classify +
                ", classtime=" + classtime +
                ", score=" + score +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                ", delFlag=" + delFlag +
                ", pic=" + pic +
                ", view=" + view +
                ", focus=" + focus +
                "}";
    }
}
