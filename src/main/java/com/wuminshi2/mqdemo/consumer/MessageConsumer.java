package com.wuminshi2.mqdemo.consumer;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
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
    //work
    @RabbitListener(queues = "work.queue")
    public void workQueue1(String message){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("workQueue1:"+message);
    }
    @RabbitListener(queues = "work.queue")
    public void workQueue2(String message){
        System.out.println("workQueue2:"+message);
    }
    //ack
    @RabbitListener(
            queues = "ack.queue",
            containerFactory = "manualAckListenerContainerFactory"
    )
    public void ackConsumer(String body, Channel channel, Message message) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            System.out.println("收到消息：" + body);
            if ("fail".equals(body)) {
                throw new RuntimeException("模拟消费失败");
            }
            channel.basicAck(deliveryTag, false);
            System.out.println("手动 ACK 成功：" + body);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);
            System.out.println("处理失败，消息重新入队：" + body);
        }
    }
    //dead letter
    @RabbitListener(queues = "dlq.normal.queue",containerFactory = "manualAckListenerContainerFactory")
    public void dlqConsumer(String body,Channel channel, Message message) throws Exception{
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            if("success".equals(body)){
                System.out.println("正常队列收到消息：" + body);
                channel.basicAck(deliveryTag,false);
            }else{
                throw new RuntimeException("模拟消费失败");
            }
        } catch (Exception e) {
            System.out.println("正常队列处理失败，准备进入死信队列：" + body);
            channel.basicNack(deliveryTag,false,false);
        }
    }
    @RabbitListener(queues = "dlq.dead.queue")
    public void deadLetterConsumer(String message) {
        System.out.println("死信队列收到：" + message);
    }
}
