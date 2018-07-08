package com.lzh.heng.rabbitmq.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {
    private static final Logger logger = LoggerFactory.getLogger(TopicSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send1(){
        String context = "hello,message 1";
        logger.info("sender :"+context);
        this.rabbitTemplate.convertAndSend("exchange","topic.message",context);
    }
    /*convertAndSend 首先是queue的方式，
    绑定queue的名称，最后context是内容*/
    public void send2(){
        String context = "hello,message 2";
        logger.info("Sender:"+context);
        this.rabbitTemplate.convertAndSend("exchange","topic.messages",context);
    }
}
