package com.wuminshi2.mqdemo.utils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Recv {
    @RabbitListener(queues = "testQueue")
    public void receive(String message){
        System.out.println("接收到的消息是："+message);
    }

}
