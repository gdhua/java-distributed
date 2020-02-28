package com.jxwifi.kyc.dev.service.tcpServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class TCPServerHandler extends ChannelInboundHandlerAdapter {
    static HashMap<String,ChannelHandlerContext> inList=new LinkedHashMap<String,ChannelHandlerContext>();


    /**
     * 新连接
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        inList.put(ctx.channel().remoteAddress().toString(),ctx);
        System.out.println("new client arrvie > " + ctx.channel().remoteAddress());
       // ctx.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
    }

    /**
     * 连接下线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        inList.remove(ctx.channel().remoteAddress().toString());
        ctx.fireChannelInactive();
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] bytes=new byte[in.readableBytes()];
        in.readBytes(bytes);
        String readMsg=HexUtils.toHexString(bytes);
        System.out.println("channelRead hex > " + readMsg);
        PackageHandlerImpl.getInstance().doHandle(readMsg);
        //ctx.writeAndFlush(in);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
         cause.printStackTrace();
         ctx.close();
    }

    /**
     * 回复信息给设备
     * @param hex
     */
    public static void RepDev(String hex){
        ByteBuf byteBuf= Unpooled.copiedBuffer(hex, CharsetUtil.UTF_8);
        ChannelHandlerContext context=getOneCtx();
        context.writeAndFlush(byteBuf);
    }

    private static ChannelHandlerContext getOneCtx() {
        for(ChannelHandlerContext i : inList.values()){
            return i;
        }
          return null;
    }


}
