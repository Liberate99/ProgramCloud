package com.yunjuanyunshu.modules.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 回答点赞表
 * </p>
 *
 * @author carl
 * @since 2018-02-05
 */
@TableName("bus_answer_like")
public class AnswerLike extends Model<AnswerLike> {

private static final long serialVersionUID = 1L;

                /**
     * id
     */
                        private String id;
            /**
     * 回答id
     */
            @TableField("answer_id")
        private String answerId;
            /**
     * 赞同者id
     */
            @TableField("user_id")
        private String userId;
            /**
     * 点赞状态，已点赞1，未点赞0
     */
            private String status;
            /**
     * 点赞时间
     */
            @TableField("create_date")
        private Date createDate;
            /**
     * 更改时间
     */
            @TableField("update_date")
        private Date updateDate;

                    
    public String getId() {
            return id;
            }

                public void setId(String id) {
                    this.id = id;
                    }
                    
    public String getAnswerId() {
            return answerId;
            }

                public void setAnswerId(String answerId) {
                    this.answerId = answerId;
                    }
                    
    public String getUserId() {
            return userId;
            }

                public void setUserId(String userId) {
                    this.userId = userId;
                    }
                    
    public String getStatus() {
            return status;
            }

                public void setStatus(String status) {
                    this.status = status;
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
    
@Override
protected Serializable pkVal() {
                return this.id;
            }

@Override
public String toString() {
        return "AnswerLike{" +
                            "id=" + id +
                                    ", answerId=" + answerId +
                                    ", userId=" + userId +
                                    ", status=" + status +
                                    ", createDate=" + createDate +
                                    ", updateDate=" + updateDate +
                    "}";
        }
        }
