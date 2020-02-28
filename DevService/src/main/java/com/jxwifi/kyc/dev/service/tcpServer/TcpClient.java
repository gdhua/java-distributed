package com.jxwifi.kyc.dev.service.tcpServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class TcpClient {

    private static final String SERVER_HOST = "192.168.27.124";

    public static void main(String[] args) {

        System.out.println("client starting....");
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(new IdleStateHandler(0, 0, 120)).
                        addLast(new TcpClientHandler());
            }
        });

        // 测试20000个连接
        for (int i = 0;  i < 20000; i++) {
            try {
                ChannelFuture channelFuture = bootstrap.connect(SERVER_HOST, 9012);
                channelFuture.addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        System.out.println("connect failed, exit!");
                    }
                });
                channelFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         System.out.println("测试ok");
        try{
            System.in.read();
        }catch (Exception e){

        }
    }

}
