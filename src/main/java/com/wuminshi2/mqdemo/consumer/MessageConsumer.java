package com.wuminshi2.mqdemo.consumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    // direct
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
    // fanout
    @RabbitListener(queues = "notify.email.queue")
    public void fanoutQueue1(String message) {
        System.out.println("email consumer 收到:" + message);
    }
    @RabbitListener(queues = "notify.sms.queue")
    public void fanoutQueue2(String message) {
        System.out.println("sms consumer 收到:" + message);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "notify.wechat.queue", durable = "true"),
            exchange= @Exchange(value = "exchange.fanout", type = ExchangeTypes.FANOUT)
    ))
    public void fanoutQueue3(String message) {
        System.out.println("wechat consumer 收到:" + message);
    }
    // topic
    @RabbitListener(queues = "topic.order.queue")
    public void topicQueue1(String message) {
        System.out.println("topicQueue1:" + message);
    }
    @RabbitListener(queues = "topic.user.queue")
    public void topicQueue2(String message) {
        System.out.println("topicQueue2:" + message);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "topic.error.queue",durable = "true"),
            exchange = @Exchange(value = "exchange.topic", type = ExchangeTypes.TOPIC),
            key = "*.error"
    ))
    public void topicQueue3(String message) {
        System.out.println("topicQueue3:" + message);
    }
}
