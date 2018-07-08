package com.lzh.heng.rabbitmq.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.messages")
public class TopicReceiver2 {

    protected static final Logger logger = LoggerFactory.getLogger(TopicReceiver2.class);

    @RabbitHandler
    public void process(String message) {
        logger.info("Topic Receiver2  : " + message);
    }

}