package com.jxwifi.kyc.service.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 模拟消息服务
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SmsApplication {

        public static void main(String[] args) {
            SpringApplication.run(SmsApplication.class, args);
        }


}
