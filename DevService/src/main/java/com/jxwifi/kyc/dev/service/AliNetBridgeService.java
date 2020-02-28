package com.jxwifi.kyc.dev.service;

import com.aliyun.iot.as.bridge.core.BridgeBootstrap;
import com.aliyun.iot.as.bridge.core.config.ConfigFactory;
import com.aliyun.iot.as.bridge.core.handler.DownlinkChannelHandler;
import com.aliyun.iot.as.bridge.core.handler.UplinkChannelHandler;
import com.aliyun.iot.as.bridge.core.model.DeviceIdentity;
import com.aliyun.iot.as.bridge.core.model.Session;
import com.aliyun.iot.as.bridge.core.session.SessionManagerFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.buf.HexUtils;

import java.util.concurrent.*;

/**
 * 阿里网桥测试
 */
public class AliNetBridgeService {

    private static final Logger log = LogManager.getLogger(AliNetBridgeService.class);

    /**
     * self-define topic template created in IoT Platform Web Console
     */
    private final static String TOPIC_TEMPLATE_USER_DEFINE = "/%s/%s/user/update";


    static AliNetBridgeService aliNetBridgeService;

    private AliNetBridgeService(){

    }

    public static  AliNetBridgeService getInstance(){
        if(aliNetBridgeService==null){
            aliNetBridgeService=new AliNetBridgeService();
        }
        return aliNetBridgeService;
    }

    /**
     * 线程池
     */
    private static ExecutorService executorService  = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setDaemon(true).setNameFormat("bridge-downlink-handle-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());


    UplinkChannelHandler uplinkChannelHandler;

    /**
     * 阿里网桥测试
     */
    public void run(){
        BridgeBootstrap bridgeBootstrap = new BridgeBootstrap();
        bridgeBootstrap.bootstrap(new DownlinkChannelHandler() {
            @Override
            public boolean pushToDevice(Session session, String topic, byte[] payload) {
                log.info("get message from cloud ==> "+HexUtils.toHexString(payload));
                executorService.submit(() -> handleDownLinkMessage(session, topic, payload));
                return true;
            }

            @Override
            public boolean broadcast(String topic, byte[] payload) {
                log.info("broadcast > get message from cloud ==> "+HexUtils.toHexString(payload));
                return false;
            }
        });

        log.info("======== Bridge bootstrap success =========");

        String originalIdentity = "demoDevice1";
        //device Session of your specific protocol, If none, you can just use empty object
        Object originalChannel = new Object();
        Session session = Session.newInstance(originalIdentity, originalChannel);
        //device online
         uplinkChannelHandler = new UplinkChannelHandler();
        boolean success=uplinkChannelHandler.doOnline(session, originalIdentity);
        log.info("======== dev doOnline > "+success+" =========");
        //pub self-define topic
        //Topic Template ${TOPIC_TEMPLATE_USER_DEFINE} is defined in IoT Platform Web Console
        sendToService(originalIdentity,"A1");
    }

    public void sendToService(String originalIdentity,String hexStr) {
        DeviceIdentity deviceIdentity = ConfigFactory.getDeviceConfigManager().getDeviceIdentity(originalIdentity);
        if (deviceIdentity == null) {
            log.error("设备未成功映射到阿里云物联网平台上的设备，丢弃消息");
            return;
        }

        Session session = SessionManagerFactory.getInstance().getSession(originalIdentity);
        if (session == null) {
            log.error("设备尚未上线，请上线设备或者丢弃消息。上报数据到物联网平台前请务必确保设备已上线。");
        }


        byte[] payload=hexStr.getBytes();
        String topic=String.format(TOPIC_TEMPLATE_USER_DEFINE, deviceIdentity.getProductKey(), deviceIdentity.getDeviceName());
        boolean success= uplinkChannelHandler.doPublish(originalIdentity, topic, payload, 0, 10);;
        if(success) {
            log.info(" 成功上报数据到阿里云物联网平台");
        } else {
            log.error(" 上报数据到阿里云物联网平台失败");
        }
    }

    private static void handleDownLinkMessage(Session session, String topic, byte[] payload) {
        String content = new String(payload);
        log.info("Get DownLink message, session:{}, topic:{}, content:{}", session, topic, content);
        Object channel = session.getChannel();
        String originalIdentity = session.getOriginalIdentity();
        //for example, you can send the message to device via channel, it depends on you specific server implementation
    }
}
