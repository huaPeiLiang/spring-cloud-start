package com.start.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.start.service.HystrixTestService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/hystrix")
public class HystrixController {

    @Resource
    private HystrixTestService hystrixTestService;

    @HystrixCommand
    @RequestMapping("/success")
    public String hystrixSuccess(){
        return hystrixTestService.hystrixSuccess();
    }

    @HystrixCommand
    @RequestMapping("/timeout")
    public String hystrixTimeOut(){
        return hystrixTestService.hystrixTimeOut();
    }

    @HystrixCommand
    @RequestMapping("/error")
    public String hystrixError() throws Exception{
        return hystrixTestService.hystrixError();
    }

}
