package com.lzh.heng.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lzh
 * @version 1.0.0
 * childhandelr 的ChannelPipeline的主流程
 * 实际上就是处理sendupstream和senddownsteam
 */
@Component
@Qualifier("springProtocolInitializer")
public class StringProtocolInitalizer extends ChannelInitializer<SocketChannel> {
    private final  AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();

    @Autowired
    StringDecoder stringDecoder;

    @Autowired
    StringEncoder stringEncoder;

    @Autowired
    ServerHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //只有通过这个类 AcceptorIdleStateTrigger才会会发生作用
        ch.pipeline().addLast(new IdleStateHandler(60,60,120, TimeUnit.SECONDS));
        ch.pipeline().addLast(idleStateTrigger);//心跳检测的实现
        /*这里是截取标志符号若截取符号之后还有那么  之后数字会随着下一次buff传过来 通过设置包头包尾截取发送的信息解决半包黏包  还有一种定长处理*/
        ByteBuf delimiter = Unpooled.copiedBuffer("123".getBytes());
        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(4096,delimiter));
        pipeline.addLast("decoder", stringDecoder);
        pipeline.addLast("handler", serverHandler);
        pipeline.addLast("encoder", stringEncoder);
    }

    public StringDecoder getStringDecoder() {
        return stringDecoder;
    }

    public void setStringDecoder(StringDecoder stringDecoder) {
        this.stringDecoder = stringDecoder;
    }

    public StringEncoder getStringEncoder() {
        return stringEncoder;
    }

    public void setStringEncoder(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }

    public ServerHandler getServerHandler() {
        return serverHandler;
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

}
