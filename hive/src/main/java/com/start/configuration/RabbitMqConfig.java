package com.start.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private static final String DIRECT_NAME = "direct";

    private static final String TOPIC_NAME_ONE = "topicOne";
    private static final String TOPIC_NAME_TWO = "topicTwo";

    private static final String TOPIC_EXCHANGE = "topicExchange";

    @Bean
    public Queue directQueue(){
        return new Queue(DIRECT_NAME);
    }

    @Bean
    public Queue topicOne(){
        return new Queue(TOPIC_NAME_ONE);
    }

    @Bean
    public Queue topicTwo(){
        return new Queue(TOPIC_NAME_TWO);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    Binding topicBindOne(Queue topicOne, TopicExchange topicExchange){
        return BindingBuilder.bind(topicOne).to(topicExchange).with(TOPIC_NAME_ONE);
    }

    @Bean
    Binding topicBindTwo(Queue topicTwo, TopicExchange topicExchange){
        return BindingBuilder.bind(topicTwo).to(topicExchange).with(TOPIC_NAME_TWO);
    }

}
