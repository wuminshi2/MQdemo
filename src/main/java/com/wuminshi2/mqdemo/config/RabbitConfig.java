package com.wuminshi2.mqdemo.config;
import org.springframework.amqp.core.*;
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

}
