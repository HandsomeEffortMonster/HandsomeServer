package com.lzh.heng.rabbitmq.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

    @Autowired
    private TopicSender topicSender;
    /*   http://localhost:9080/send1   */
    @RequestMapping("/send1")
    public String send1() {
        topicSender.send1();
        return "send1 ok";
    }
    /*   http://localhost:9080/send2 */
    @RequestMapping("/send2")
    public String send2() {
        topicSender.send2();
        return "send2 ok";
    }
}