package com.wuminshi2.mqdemo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class DirectExchangeController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/created")
    public void sendCreatedMessage (@RequestParam(value = "message",defaultValue = "") String message){
        rabbitTemplate.convertAndSend("exchange.direct","order.created",message);
    }
    @RequestMapping("/cancelled")
    public void sendCancelledMessage (@RequestParam(value = "message",defaultValue = "") String message){
        rabbitTemplate.convertAndSend("exchange.direct","order.cancelled",message);
    }
}
