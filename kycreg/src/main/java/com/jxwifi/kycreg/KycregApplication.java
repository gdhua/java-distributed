package com.jxwifi.kycreg;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册中心
 */
@EnableEurekaServer
@SpringBootApplication
public class KycregApplication {

    private static final Logger logger = LoggerFactory.getLogger(KycregApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KycregApplication.class, args);
        logger.info("注册服务中心启动OK。");
    }

}
