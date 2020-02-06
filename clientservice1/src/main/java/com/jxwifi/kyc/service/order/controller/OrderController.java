package com.jxwifi.kyc.service.order.controller;

import com.jxwifi.kyc.service.order.service.SmsService;
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
    @RequestMapping("/order/hi")
    public String home(@RequestParam String name) {
        String result= smsService.sayHiFromClientOne("tset");
        System.out.println("call sms ret="+result);
        return "hi "+name+",i am from port:" +port;
    }
}
