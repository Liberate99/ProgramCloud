package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author carl
 * @since 2017-11-09
 */
@TableName("bus_college_user")
public class BusCollegeUser extends Model<BusCollegeUser> {

private static final long serialVersionUID = 1L;

                                private String id;
                @TableField("user_id")
        private String userId;
            /**
     * 学号/教工号
     */
            @TableField("work_id")
        private String workId;
            /**
     * 学校
     */
            private String college;
            /**
     * 系/学院
     */
            private String department;
            /**
     * 专业
     */
            private String major;
            /**
     * 职称(教师用)
     */
            private String rank;
            /**
     * 个人简介(教师用)
     */
            private String desc;

                    
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
                    
    public String getCollege() {
            return college;
            }

                public void setCollege(String college) {
                    this.college = college;
                    }
                    
    public String getDepartment() {
            return department;
            }

                public void setDepartment(String department) {
                    this.department = department;
                    }
                    
    public String getMajor() {
            return major;
            }

                public void setMajor(String major) {
                    this.major = major;
                    }
                    
    public String getRank() {
            return rank;
            }

                public void setRank(String rank) {
                    this.rank = rank;
                    }
                    
    public String getDesc() {
            return desc;
            }

                public void setDesc(String desc) {
                    this.desc = desc;
                    }
    
@Override
protected Serializable pkVal() {
                return this.id;
            }

@Override
public String toString() {
        return "BusCollegeUser{" +
                            "id=" + id +
                                    ", userId=" + userId +
                                    ", workId=" + workId +
                                    ", college=" + college +
                                    ", department=" + department +
                                    ", major=" + major +
                                    ", rank=" + rank +
                                    ", desc=" + desc +
                    "}";
        }
        }
