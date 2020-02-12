package com.jxwifi.kyc.service.sms.service.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 第一个消费者
 */
@Service
public class ConsumerServiceImpl {
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息 根据配置文件只能接收一种类型
   /* @JmsListener(destination = "test_hua")
    public void receiveQueue(String text) {
        System.out.println("收到的报文为:"+text);
    }*/

    /**
     * 指定接收队列 类型
     * @param text
     * @return
     */
  @JmsListener(destination = "test_hua", containerFactory = "queueListenerFactory")
    public void receiveQueue(String text) {

        System.out.println("Consumer_queue 收到的报文为:"+text);

        //return "return message" + text;
    }

    /**
     * 指定接收主题 类型
     * @param text
     * @return
     */
    @JmsListener(destination = "test_hua", containerFactory = "topicListenerFactory")
    public void receiveTopic(String text) {

        System.out.println("Consumer_topic 收到的报文为:"+text);

    }

}
