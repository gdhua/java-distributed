package com.jxwifi.kyc.service.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 模拟订单服务
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OrderApplication {

        public static void main(String[] args) {
            SpringApplication.run(OrderApplication.class, args);
        }


}
