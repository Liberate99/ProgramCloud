package com.yunjuanyunshu.modules.messenger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author carl
 * @date 2018/02/12
 */
@Component
public class MessageSender {

    /**
     * 发送消息至消息队列.
     * @param mapMessage - Key-Value格式的消息
     */
    public void sendMessage(final Map<String, Object> mapMessage) {
        long submissionId = (Long) mapMessage.get("submissionId");

        jmsTemplate.convertAndSend(mapMessage);
        LOGGER.info(String.format("Submission task #%d has been created.", new Object[] {submissionId}));
    }

    /**
     * 自动注入的JmsTemplate对象.
     * 用于发送消息至消息队列.
     */
    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);
}
