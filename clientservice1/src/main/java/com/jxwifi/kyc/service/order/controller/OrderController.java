package com.jxwifi.kyc.service.order.controller;

import com.jxwifi.kyc.service.order.service.SmsService;
import com.jxwifi.kyc.service.order.service.mq.ProducerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderController {
    @Autowired
    SmsService smsService;

    @Value("${server.port}")
    String port;

    @Autowired
    ProducerServiceImpl producerService;

    /**
     * 测试服务互调
     *
     * @param name
     * @return
     */
    @RequestMapping("/order/hi")
    public String callService(@RequestParam String name) {
        String result = smsService.sayHiFromClientOne("tset");
        System.out.println("call sms ret=" + result);
        return "hi " + name + ",i am from port:" + port;
    }


    /**
     * 测试MQ发送信息
     * @param msg
     * @return
     */
    @RequestMapping("/mq/hi")
    public String sendMsg(@RequestParam String msg) {
        producerService.sendMessage("test_hua", msg );
        return "发送成功";
    }

    /**
     * 测试MQ发送信息 多类型
     * @param msg
     * @return
     */
    @RequestMapping("/mq/hi2")
    public String sendMsg2(@RequestParam String msg) {
        for (int i = 0; i < 5; i++) {
            //producerService.sendMessageByTopic("test_hua", msg + i);
            producerService.sendMessageByQueue("test_hua", msg + i);

        }
        return "发送成功";
    }
}
