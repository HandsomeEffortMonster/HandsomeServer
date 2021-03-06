package com.lzh.heng.rabbitmq.example;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.amqp.core.Queue;

@Configuration
public class TopicRabbitConfig {
    final static String message = "topic.message";
    final static String messages = "topic.messages";

    @Bean
    public Queue queueMessage(){
        return new Queue(TopicRabbitConfig.message);

    }
    @Bean
    public Queue queueMessages(){
        return new Queue(TopicRabbitConfig.messages);
    }
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    /**
     * @param queueMessage
     * @param exchange
     * @return
     * 这里bindingExchangeMessage 是绑定queue与topic通过通道的key 不同 topic的msg "topic.message"
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }


}
