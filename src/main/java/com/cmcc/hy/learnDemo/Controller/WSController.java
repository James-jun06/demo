package com.cmcc.hy.learnDemo.Controller;

import com.cmcc.hy.learnDemo.Dto.Greeting;
import com.cmcc.hy.learnDemo.Dto.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * Project:  learnDemo
 * File Created by James on 2018/6/27
 *
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 */
@Controller
public class WSController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @MessageMapping("/sendTest")
    @SendTo("/topic/subscribeTest")
    public Greeting sendDemo(HelloMessage message) {
        logger.info("接收到了信息" + message.getName());
        return new Greeting("你发送的消息为:" + message.getName());
    }

    @SubscribeMapping("/subscribeTest")
    public Greeting sub() {
        logger.info("XXX用户订阅了我。。。");
        return new Greeting("感谢你订阅了我。。。");
    }

}
