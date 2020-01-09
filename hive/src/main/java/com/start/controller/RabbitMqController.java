package com.start.controller;

import com.start.service.HystrixTestService;
import com.start.service.RabbitMqService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/rabbit")
public class RabbitMqController {

    @Resource
    private RabbitMqService rabbitMqService;

    @RequestMapping("/send")
    public void directSend(){
        rabbitMqService.directSend();
    }

}
