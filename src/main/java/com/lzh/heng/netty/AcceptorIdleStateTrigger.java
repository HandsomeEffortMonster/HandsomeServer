package com.lzh.heng.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author lzh
 * @version 1.0.0
 * 处理netty中的心跳检测的类
 */
@ChannelHandler.Sharable
public class AcceptorIdleStateTrigger extends ChannelHandlerAdapter{
    //readerIdleTimeSeconds, 读超时. 即当在指定的事件间隔内没有从 Channel 读取到数据时, 会触发一个 READER_IDLE 的 IdleStateEvent 事件.
    //writerIdleTimeSeconds, 写超时. 即当在指定的事件间隔内没有数据写入到 Channel 时, 会触发一个 WRITER_IDLE 的 IdleStateEvent 事件.
    //allIdleTimeSeconds, 读/写超时. 即当在指定的事件间隔内没有读或写操作时, 会触发一个 ALL_IDLE 的 IdleStateEvent 事件.
    @Override//心跳检测触发
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        //  实现其userEventTriggered()方法，在出现超时事件时会被触发，包括读空闲超时或者写空闲超时；
        if(evt instanceof IdleStateEvent){//instanceof 表示运算符用来在运行时指出对象是否是特定的一个实例
            IdleState state = ((IdleStateEvent) evt).state();
            if(state == IdleState.READER_IDLE){
                handleReaderIdle(ctx);
            }else if(state == IdleState.WRITER_IDLE){
                handleWriterIdle(ctx);
            }else if(state == IdleState.ALL_IDLE){
                handleAllIdle(ctx);
            }
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * @param ctx
     * 对没du的情况的处理
     */
    protected void handleReaderIdle(ChannelHandlerContext ctx){
        System.out.println("客户端:"+ctx.channel().remoteAddress().toString()+"读超时");
        ctx.close();
        /*这里也可以尝试重新连接*/
        /*这里也可以尝试重新连接*/
    }

    /**
     * @param ctx
     * 处理长时间没写
     */
    protected void handleWriterIdle(ChannelHandlerContext ctx){

        System.err.println("客户端:"+ctx.channel().remoteAddress().toString()+"写超时");
        ctx.close();
    }

    /**
     * @param ctx
     * 两边没写或者没读的处理总超时
     */
    protected void handleAllIdle(ChannelHandlerContext ctx){

        System.err.println("客户端:"+ctx.channel().remoteAddress().toString()+"读写总长超时");
        ctx.close();
    }



}
