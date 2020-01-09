package com.start.service.impl;

import com.start.service.RabbitMqService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final String DIRECT_NAME = "direct";
    private static final String TOPIC_NAME_ONE = "topicOne";
    private static final String TOPIC_NAME_TWO = "topicTwo";
    private static final String TOPIC_EXCHANGE = "topicExchange";

    @Override
    public void directSend(){
        amqpTemplate.convertAndSend(DIRECT_NAME, "test");
    }

    @Override
    public void topicSend(){
        amqpTemplate.convertAndSend(TOPIC_EXCHANGE,TOPIC_NAME_ONE, "test");
    }


    @RabbitListener( queues=DIRECT_NAME )
    public void directWork(String message){
        System.out.println(message);
    }

    @RabbitListener( queues=TOPIC_NAME_ONE )
    public void topicWork(String message){
        System.out.println(message);
    }

}
