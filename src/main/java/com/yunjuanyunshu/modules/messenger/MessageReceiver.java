package com.yunjuanyunshu.modules.messenger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Calendar;
import java.util.Date;

/**
 * @author carl
 * @date 2018/02/24
 */
@Component
public class MessageReceiver implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            final MapMessage mapMessage = (MapMessage) message;

            try {
                String event = mapMessage.getString("event");

                if ("KeepAlive".equals(event)) {
                    receiveFromAliveJudgersHandler(mapMessage);
                } else {
                    LOGGER.warn(String.format("Unknown Event Received. [Event = %s]",
                            new Object[] { event }));
                }
            } catch (JMSException ex) {
                LOGGER.error("JMSException message: ", ex);
            }
        }
    }

    /**
     * 处理来自评测机的Keep-Alive消息.
     * 用于在Web端获取后端评测机的信息.
     * @param mapMessage - 消息队列中收到的MapMessage对象
     * @throws JMSException
     */
    private void receiveFromAliveJudgersHandler(MapMessage mapMessage) throws JMSException {
        String judgerUsername = mapMessage.getString("username");
        String judgerDescription = mapMessage.getString("description");
        long heartbeatTimeInMillis = mapMessage.getLong("heartbeatTime");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(heartbeatTimeInMillis);
        Date heartbeatTime = calendar.getTime();

        eventPublisher.publishEvent(new KeepAliveEvent(this, judgerUsername, judgerDescription, heartbeatTime));
        LOGGER.info(String.format("Received heartbeat from Judger[%s]", judgerUsername));
    }

    /**
     * 自动注入的ApplicationEventPublisher对象.
     * 用于完成接收到来自评测机的消息后的消息分发操作.
     */
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * 日志记录器.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class);
}
