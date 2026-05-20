package com.wuminshi2.mqdemo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work")
public class WorkQueueController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping
    public void send(@RequestParam(value = "message",defaultValue = "")String  message){
        for(int i = 0; i < 10; i++){
            rabbitTemplate.convertAndSend("work.queue",message+i);
        }
    }

}
