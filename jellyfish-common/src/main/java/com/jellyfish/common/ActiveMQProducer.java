/*
 * @Author: mikey.zhaopeng 
 * @Date: 2019-08-20 10:00:50 
 * @Last Modified by:   mikey.zhaopeng 
 * @Last Modified time: 2019-08-20 10:00:50 
 */

package com.jellyfish.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.UUID;

@Component
public class ActiveMQProducer {

    @Autowired
    private JmsMessagingTemplate jmsTemplate;
    @Autowired
    private Queue queue;
    @Autowired
    private Topic topic;

    public void sendQueueMsg(String msg) {
        jmsTemplate.convertAndSend(queue, msg);
    }

    public void sendQueueMsg(byte[] msg) {
        jmsTemplate.convertAndSend(queue, msg);
    }

    public void sendQueueMsg(Object msg) {
        jmsTemplate.convertAndSend(queue, msg);
    }

    public void sendTopicMsg(String msg) {
        jmsTemplate.convertAndSend(topic, msg);
    }

    public void sendTopicMsg(byte[] msg) {
        jmsTemplate.convertAndSend(topic, msg);
    }

    public void sendTopicMsg(Object msg) {
        jmsTemplate.convertAndSend(topic, msg);
    }

    @Scheduled(fixedDelay = 50000)
    public void send() {
        this.sendQueueMsg(UUID.randomUUID().toString());
        this.sendTopicMsg(UUID.randomUUID().toString());
    }
}
