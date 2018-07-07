package com.lzh.heng.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author lzh
 * @version 1.0.0
 * 自己定义的消息处理handler
 */
@Component
@Qualifier("serverHandler")
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg)
            throws Exception {
        log.info("client msg:"+msg);
        String clientIdToLong= ctx.channel().id().asLongText();
        log.info("client long id:"+clientIdToLong);
        String clientIdToShort= ctx.channel().id().asShortText();
        log.info("client short id:"+clientIdToShort);
        //当收到bye时候关闭通道
        if(msg.indexOf("bye")!=-1){
            //close
            ctx.channel().close();
        }else{
            //send to client
            ctx.channel().writeAndFlush("Yoru msg is:"+msg);

        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        log.info("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

        /*netty中维护一个连接表 这样如果需要主动发送的话就通过这个东西主动下发*/
        TCPServer.getChannelMap().put(ctx.channel().remoteAddress().toString()
                ,ctx.channel());

        ctx.channel().writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

        super.channelActive(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * @param ctx
     * @throws Exception
     * ctx，close会触发这个类  注销表中数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        TCPServer.getChannelMap().remove(ctx.channel().remoteAddress().toString());
        log.info("\nChannel is disconnected");
        super.channelInactive(ctx);
    }




}

