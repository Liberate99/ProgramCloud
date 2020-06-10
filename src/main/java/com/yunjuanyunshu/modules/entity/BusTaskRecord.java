package com.yunjuanyunshu.modules.entity;

import java.util.Date;
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
 * @since 2018-04-07
 */
@TableName("bus_task_record")
public class BusTaskRecord extends Model<BusTaskRecord> {

private static final long serialVersionUID = 1L;

                                private String id;
                @TableField("task_id")
        private String taskId;
                @TableField("user_id")
        private String userId;
            /**
     * 0-未完成 1-完成
     */
            @TableField("has_finish")
        private Integer hasFinish;
                @TableField("finish_time")
        private Date finishTime;

                    
    public String getId() {
            return id;
            }

                public void setId(String id) {
                    this.id = id;
                    }
                    
    public String getTaskId() {
            return taskId;
            }

                public void setTaskId(String taskId) {
                    this.taskId = taskId;
                    }
                    
    public String getUserId() {
            return userId;
            }

                public void setUserId(String userId) {
                    this.userId = userId;
                    }
                    
    public Integer getHasFinish() {
            return hasFinish;
            }

                public void setHasFinish(Integer hasFinish) {
                    this.hasFinish = hasFinish;
                    }
                    
    public Date getFinishTime() {
            return finishTime;
            }

                public void setFinishTime(Date finishTime) {
                    this.finishTime = finishTime;
                    }
    
@Override
protected Serializable pkVal() {
                return this.id;
            }

@Override
public String toString() {
        return "BusTaskRecord{" +
                            "id=" + id +
                                    ", taskId=" + taskId +
                                    ", userId=" + userId +
                                    ", hasFinish=" + hasFinish +
                                    ", finishTime=" + finishTime +
                    "}";
        }
        }
