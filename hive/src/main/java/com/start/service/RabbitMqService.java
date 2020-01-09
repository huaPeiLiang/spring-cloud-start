package com.start.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public interface RabbitMqService {

    void directSend();

    void topicSend();

}
