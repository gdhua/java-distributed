package com.jxwifi.kyc.dev.service.tcpServer;

import io.netty.buffer.ByteBuf;

/**
 * 包处理
 */
public interface PackageHandler {
     void doHandle(String hex) ;

}
