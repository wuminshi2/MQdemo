package com.wuminshi2.mqdemo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dlq")
public class DlqController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @PostMapping
    public void send(@RequestParam(value = "message", defaultValue="")String  message){
        rabbitTemplate.convertAndSend("dlq.normal.queue",message);
    }
}
