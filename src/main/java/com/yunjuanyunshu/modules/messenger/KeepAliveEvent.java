package com.yunjuanyunshu.modules.messenger;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

/**
 * @author carl
 * @date 2018/02/12
 */

public class KeepAliveEvent  extends ApplicationEvent {

    public KeepAliveEvent(Object source, String judgerUsername, String judgerDescription, Date heartbeatTime) {
        super(source);
        this.judgerUsername = judgerUsername;
        this.judgerDescription = judgerDescription;
        this.heartbeatTime = heartbeatTime;
    }

    public String getJudgerUsername() {
        return judgerUsername;
    }

    public String getJudgerDescription() {
        return judgerDescription;
    }

    public Date getHeartbeatTime() {
        return heartbeatTime;
    }

    /**
     * 评测机的用户名.
     */
    private final String judgerUsername;

    /**
     * 评测机的描述信息.
     */
    private final String judgerDescription;

    /**
     * 评测机心跳的时间.
     */
    private final Date heartbeatTime;


}
