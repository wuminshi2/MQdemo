package com.wuminshi2.mqdemo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic")
public class TopicExchangeController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @PostMapping("/order")
    public void sendOrderMessage(@RequestParam(value="message",defaultValue = "")String  message){
        rabbitTemplate.convertAndSend("exchange.topic","order.created",message);
    }
    @PostMapping("/user")
    public void sendUserMessage(@RequestParam(value="message",defaultValue = "")String  message){
        rabbitTemplate.convertAndSend("exchange.topic","user.created",message);
    }
    @PostMapping("/orderError")
    public void sendOrderErrorMessage(@RequestParam(value="message",defaultValue = "")String  message){
        rabbitTemplate.convertAndSend("exchange.topic","order.error",message);
    }
}