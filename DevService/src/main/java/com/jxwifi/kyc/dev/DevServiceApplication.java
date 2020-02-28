package com.jxwifi.kyc.dev;

import com.jxwifi.common.util.TimeUtil;
import com.jxwifi.kyc.dev.service.AliNetBridgeService;
import com.jxwifi.kyc.dev.service.tcpServer.PackageHandlerByAli;
import com.jxwifi.kyc.dev.service.tcpServer.PackageHandlerByMQ;
import com.jxwifi.kyc.dev.service.tcpServer.PackageHandlerImpl;
import com.jxwifi.kyc.dev.service.tcpServer.TcpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * 设备通讯服务
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.jxwifi")
public class DevServiceApplication {
    private static final Logger logger = LogManager.getLogger(DevServiceApplication.class);
    public static ConfigurableApplicationContext ac;



    public static void main(String[] args) {

        logger.info(" networkTime > "+ TimeUtil.getNetworkTime());
        logger.info(" diffTime > "+ TimeUtil.getNetwordTimeDiffSecnod());

        Long startTime = System.currentTimeMillis();

        ac=SpringApplication.run(DevServiceApplication.class, args);

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        logger.info(runtimeMXBean.getName());
        logger.info("Spec Name is "+runtimeMXBean.getSpecName());
        logger.info("Spec Vendor is "+runtimeMXBean.getSpecVendor());
        logger.info("Spec Version is "+runtimeMXBean.getSpecVersion());
        logger.info("VM Name is "+runtimeMXBean.getVmName());
        logger.info("VM Vendor is "+runtimeMXBean.getVmVendor());
        logger.info("VM Version is "+runtimeMXBean.getVmVersion());
        Long endTime = System.currentTimeMillis();
        logger.info("启动时间(ms):"+(endTime-startTime)/1000);

        AliNetBridgeService aliNetBridgeService=AliNetBridgeService.getInstance();
        aliNetBridgeService.run();

        TcpServer tcpServer=TcpServer.getInstance();
        try {
            tcpServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PackageHandlerImpl.getInstance().addHandle(new PackageHandlerByAli()).addHandle(new PackageHandlerByMQ());


    }

}
