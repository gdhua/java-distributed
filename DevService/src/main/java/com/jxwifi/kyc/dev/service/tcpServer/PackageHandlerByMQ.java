package com.jxwifi.kyc.dev.service.tcpServer;

import com.jxwifi.kyc.dev.DevServiceApplication;
import com.jxwifi.kyc.dev.service.TranServiceImpl;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class PackageHandlerByMQ  extends PackageHandlerImpl{
    private final String in_name="tcp_dev_in";

    @Override
    public void doHandle(String hexStr) {
        TranServiceImpl tranService=DevServiceApplication.ac.getBean(TranServiceImpl.class);

            tranService.sendMessage(in_name,hexStr);
            System.out.println("HandlerByMQ ok  >>> "+hexStr);


    }
}
