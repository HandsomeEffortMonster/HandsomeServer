package com.lzh.heng.Task;

import com.lzh.heng.netty.ServerHandler;
import com.lzh.heng.netty.TCPServer;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * 定时任务处理类
 */
@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);


    //@Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
    //@Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
    //@Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
    //@Scheduled(cron="*/5 * * * * *") ：通过cron表达式定义规则

    /**
     *定时任务对表中存在的连接 打印并发送心跳包
     */
    private String connectMsg = "go on";
    @Scheduled(fixedRate = 5000*2)
    public void reportCurrentChannelMap(){
        for(Map.Entry<String, Channel> entry: TCPServer.getChannelMap().entrySet()){
            log.info("地址："+entry.getKey()+"仍保持连接");
            entry.getValue().writeAndFlush(connectMsg);
        }
    }
}
