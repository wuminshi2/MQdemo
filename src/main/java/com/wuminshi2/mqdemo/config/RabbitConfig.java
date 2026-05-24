package com.wuminshi2.mqdemo.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //test队列
    @Bean
    public Queue testQueue(){
        return new Queue("testQueue",true);
    }
    //direct exchange
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("exchange.direct");
    }
    @Bean
    public Queue directQueue1(){
        return new Queue("order.created.queue1",true);
    }
    @Bean
    public Binding bindingDirectQueue1(Queue directQueue1, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue1).to(directExchange).with("order.created");
    }
    //fanout  exchange
    @Bean
    public FanoutExchange fanoutExchange(){return new FanoutExchange("exchange.fanout");}
    @Bean
    public Queue fanoutQueue1(){return new Queue("notify.email.queue",true);}
    @Bean
    public Queue fanoutQueue2(){return new Queue("notify.sms.queue",true);}
    @Bean
    public Binding bindingFanoutQueue1(Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }
    @Bean
    public Binding bindingFanoutQueue2(Queue fanoutQueue2, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
    //topic exchange
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("exchange.topic");
    }
    @Bean
    public Queue topicQueue1(){return new Queue("topic.order.queue", true);}
    @Bean
    public Queue topicQueue2(){return new Queue("topic.user.queue", true);}
    @Bean
    public Binding bindingTopicQueue1(Queue topicQueue1, TopicExchange topicExchange){
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with("order.*");
    }
    @Bean
    public Binding bindingTopicQueue2(Queue topicQueue2, TopicExchange topicExchange){
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("user.*");
    }
    //work queue
    @Bean
    public Queue workQueue(){return new Queue("work.queue", true);}
    //ackQueue
    @Bean
    public Queue ackQueue(){
        return new Queue("ack.queue", true);
    }
    @Bean
    public SimpleRabbitListenerContainerFactory manualAckListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setPrefetchCount(1);
        return factory;
    }
    //dlq queue
    @Bean
    public Queue dlqNormalQueue(){
        return QueueBuilder.durable("dlq.normal.queue")
                .deadLetterExchange("dlq.exchange")
                .deadLetterRoutingKey("dlq.dead")
                .build();
    }
    @Bean
    public Queue  dlqDeadQueue(){
        return new Queue("dlq.dead.queue", true);
    }
    @Bean
    public DirectExchange dlqExchange(){
        return new DirectExchange("dlq.exchange");
    }
    @Bean
    public Binding bindingDeadLetterQueue(Queue dlqDeadQueue, DirectExchange dlqExchange){
        return BindingBuilder.bind(dlqDeadQueue).to(dlqExchange).with("dlq.dead");
    }
}


