package com.yunjuanyunshu.modules.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 积分明细表
 * </p>
 *
 * @author carl
 * @since 2018-02-05
 */
@TableName("bus_reward_points")
public class RewardPoints extends Model<RewardPoints> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 积分数量
     */
    @TableField("reward_points")
    private Integer rewardPoints;
    /**
     * 赚取/花费
     */
    private String type;
    /**
     * 用户行为
     */
    private String event;
    /**
     * 操作时间
     */
    @TableField("event_time")
    private Date eventTime;


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

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RewardPoints{" +
                "id=" + id +
                ", userId=" + userId +
                ", rewardPoints=" + rewardPoints +
                ", type=" + type +
                ", event=" + event +
                ", eventTime=" + eventTime +
                "}";
    }
}
