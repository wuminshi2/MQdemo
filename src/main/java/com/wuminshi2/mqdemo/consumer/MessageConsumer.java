package com.wuminshi2.mqdemo.consumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    @RabbitListener(queues = "order.created.queue1")
    public void directQueue1(String message) {
        System.out.println("directQueue1:" + message);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order.cancelled.queue2", durable = "true"),
            exchange= @Exchange(value = "exchange.direct", type = ExchangeTypes.DIRECT),
            key="order.cancelled"
    ))
    public void directQueue2(String message) {
        System.out.println("directQueue2:" + message);
    }
}
