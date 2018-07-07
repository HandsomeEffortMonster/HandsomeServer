package com.lzh.heng.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lzh
 * @version 1.0.0
 * netty服务端搭建
 * 如果需要给某一段ip主动发送数据，就根据channelmap中
 */
@Component
public class TCPServer {
    /*在tcp  Server中维护两个表*/
    private static Map<String,Channel> channelMap = new ConcurrentHashMap<String, Channel>();
    private static Map<String,byte[]> messageMap = new ConcurrentHashMap<String, byte[]>();

    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap b;

    @Autowired
    @Qualifier("tcpSocketAddress")
    private InetSocketAddress tcpPort;

    private ChannelFuture serverChannelFuture;

    @PostConstruct
    public void start() throws Exception {
        System.out.println("Starting server at " + tcpPort);
        serverChannelFuture = b.bind(tcpPort).sync();
    }

    @PreDestroy
    public void stop() throws Exception {
        serverChannelFuture.channel().closeFuture().sync();
    }

    public ServerBootstrap getB() {
        return b;
    }

    public void setB(ServerBootstrap b) {
        this.b = b;
    }

    public InetSocketAddress getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(InetSocketAddress tcpPort) {
        this.tcpPort = tcpPort;
    }

    /*
    * 维护的两张表的操作
    * */
    /**
     * @return the messageMap
     */
    public static Map<String, byte[]> getMessageMap() {
        return messageMap;
    }

    /**
     * @param messageMap the messageMap to set
     */
    public static void setMessageMap(Map<String, byte[]> messageMap) {
        TCPServer.messageMap = messageMap;
    }

    /**
     * @return channel map get
     */
    public static Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    /**
     * @param map channel map set
     */
    public static void setChannelMap(Map<String, Channel> map) {
        TCPServer.channelMap = map;
    }

}
