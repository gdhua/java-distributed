package com.jxwifi.kycreg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册中心
 */
@EnableEurekaServer
@SpringBootApplication
public class KycregApplication {

    public static void main(String[] args) {
        SpringApplication.run(KycregApplication.class, args);
    }

}
