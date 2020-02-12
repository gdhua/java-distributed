package com.jxwifi.kyc.service.order.service.mq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * 消息生产者
 */
@Service
public class ProducerServiceImpl {

    /**
     * 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
     */
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    /**
     * 发送消息 采用系统配置类型
     * @param queueName 是发送到的队列名称
     * @param message 是发送到的队列
     */
    public void sendMessage(String queueName, final String message){
        jmsTemplate.convertAndSend(queueName, message);
    }

    /**
     * 发送消息 采用指定主题类型
     * @param queueName 是发送到的队列
     * @param message 是发送到的队列
     */
    public void sendMessageByTopic(String queueName, final String message){
        Destination destination=new ActiveMQTopic(queueName);
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * 发送消息 采用指定队列类型
     * @param queueName 是发送到的队列
     * @param message 是发送到的队列
     */
    public void sendMessageByQueue(String queueName, final String message){
        Destination destination=new ActiveMQQueue(queueName);
        jmsTemplate.convertAndSend(destination, message);
    }
}
