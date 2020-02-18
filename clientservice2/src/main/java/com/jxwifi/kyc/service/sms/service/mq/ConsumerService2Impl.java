package com.jxwifi.kyc.service.sms.service.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 第二人个消费者
 */
@Service
public class ConsumerService2Impl {
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "test_hua")
    public void receiveQueue(String text) {
        System.out.println("Consumer2收到的报文为:" + text);
    }
}
