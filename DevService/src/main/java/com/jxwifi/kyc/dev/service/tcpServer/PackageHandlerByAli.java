package com.jxwifi.kyc.dev.service.tcpServer;

import com.jxwifi.kyc.dev.service.AliNetBridgeService;
import io.netty.buffer.ByteBuf;
import org.apache.tomcat.util.buf.HexUtils;

public class PackageHandlerByAli extends PackageHandlerImpl {
    @Override
    public void doHandle(String hex) {
        System.out.println("PackageHandlerByAli");
        AliNetBridgeService.getInstance().sendToService("demoDevice1", hex);
    }
}
