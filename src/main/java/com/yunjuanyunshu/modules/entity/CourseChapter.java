package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
@TableName("bus_course_chapter")
public class CourseChapter extends Model<CourseChapter> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    @TableId("course_id")
	private String courseId;
    /**
     * 章节编号
     */
	@TableField("chapter_id")
	private String chapterId;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	@Override
	protected Serializable pkVal() {
		return this.getChapterId()+this.getCourseId();
	}
}
