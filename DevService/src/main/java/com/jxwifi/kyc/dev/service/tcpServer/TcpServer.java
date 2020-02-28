package com.jxwifi.kyc.dev.service.tcpServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServer {

    private Logger log = LoggerFactory.getLogger(getClass());
    //端口号
    private int port=9012;

    static TcpServer tcpServer;
    private TcpServer(){

    }

    public static TcpServer getInstance(){
        if(tcpServer==null){
            tcpServer=new TcpServer();
        }
        return tcpServer;
    };

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
            @Override
            public void initChannel(SocketChannel ch)throws Exception{
                ch.pipeline().addLast(new TCPServerHandler());
            }
        });

        bootstrap.bind(port).addListener((ChannelFutureListener) future -> {
            log.info("bind success in port: " + port);
        });
        System.out.println("server started!");


    }
}
