package com.jxwifi.kyc.dev.service;

import com.jxwifi.kyc.dev.service.tcpServer.TCPServerHandler;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

@Service
public class TranServiceImpl {


    /**
     * 接受服务器 返回数据
     */
    private final String out_name="tcp_dev_out";

    /**
     * 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
     */
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    /**
     * 发送消息 采用系统配置类型
     *
     * @param queueName 是发送到的队列名称
     * @param message   是发送到的队列
     */
    public void sendMessage(String queueName, final String message) {
        jmsTemplate.convertAndSend(queueName, message);
    }

    /**
     * 发送消息 采用指定队列类型
     *
     * @param queueName 是发送到的队列
     * @param message   是发送到的队列
     */
    public void sendMessageByQueue(String queueName, final String message) {
        Destination destination = new ActiveMQQueue(queueName);
        jmsTemplate.convertAndSend(destination, message);
    }

    @JmsListener(destination = out_name)
    public void receiveQueue(String text) {
        System.out.println("回复到设备报文:"+text);
        TCPServerHandler.RepDev(text);
    }
}
