package com.wuminshi2.mqdemo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ack")
public class AckQueueController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping
    public void send(String message){
        rabbitTemplate.convertAndSend("ack.queue",message);
    }
}
