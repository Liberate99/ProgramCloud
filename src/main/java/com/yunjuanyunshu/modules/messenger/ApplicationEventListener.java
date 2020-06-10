package com.yunjuanyunshu.modules.messenger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author carl
 * @date 2018/02/12
 */
@Component
public class ApplicationEventListener {
    /**
     * ApplicationEventListener的构造函数.
     */
    public ApplicationEventListener() {
        synchronized (this) {
            if ( scheduler == null ) {
                final int INITIAL_DELAY = 0;
                final int PERIOD = 30;

                scheduler = Executors.newScheduledThreadPool(1);
                scheduler.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.MINUTE, -PERIOD);
                        Date heartbeatTimeDeadline = calendar.getTime();

                        for ( Iterator<Map.Entry<String, Map<String, Object>>>
                              itr = onlineJudgers.entrySet().iterator(); itr.hasNext(); ) {
                            Map.Entry<String, Map<String, Object>> entry = itr.next();
                            Date lastHeartbeatTime = (Date) entry.getValue().get("heartbeatTime");

                            if ( !lastHeartbeatTime.after(heartbeatTimeDeadline) ) {
                                itr.remove();
                            }
                        }
                    }
                }, INITIAL_DELAY, PERIOD, TimeUnit.MINUTES);
            }
        }
    }

    /**
     * 处理评测机心跳事件.
     * @param event - 评测机心跳事件
     */
    @EventListener
    public void keepAliveEventHandler(KeepAliveEvent event) {
        String judgerUsername = event.getJudgerUsername();
        String judgerDescription = event.getJudgerDescription();
        Date heartbeatTime = event.getHeartbeatTime();

        Map<String, Object> judgerInformation = new HashMap<>();
        judgerInformation.put("description", judgerDescription);
        judgerInformation.put("heartbeatTime", heartbeatTime);

        onlineJudgers.put(judgerUsername, judgerInformation);
    }

    /**
     * 获取评测机的描述信息.
     * @param judgerUsername - 评测机的用户名
     * @return 评测机的描述信息
     */
    public String getJudgerDescription(String judgerUsername) {
        String judgerDescription = "[Offline]";

        if ( onlineJudgers.containsKey(judgerUsername) ) {
            String description = (String) onlineJudgers.get(judgerUsername).get("description");
            judgerDescription = "[Online] " + description;
        }
        return judgerDescription;
    }

    /**
     * 获取在线评测机的数量.
     * @return 在线评测机的数量
     */
    public long getOnlineJudgers() {
        return onlineJudgers.size();
    }

    /**
     * 在线评测机的列表.
     * Map中的Key表示评测机的用户名.
     * Map中的Value表示对应评测机的信息.
     */
    private static Map<String, Map<String, Object>> onlineJudgers = new Hashtable<String, Map<String, Object>>();

    /**
     * ScheduledExecutorService对象.
     * 用于定期移除离线的评测机.
     */
    private static ScheduledExecutorService scheduler = null;

    /**
     * 日志记录器.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class);
}
