package com.start.service;

public interface RabbitMqService {

    void directSend();

    void topicSend();

    String test();
}
