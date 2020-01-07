package com.start.controller;

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

    @RequestMapping("/success")
    public String hystrixSuccess(){
        return hystrixTestService.hystrixSuccess();
    }

    @RequestMapping("/timeout")
    public String hystrixTimeOut(){
        return hystrixTestService.hystrixTimeOut();
    }

    @RequestMapping("/error")
    public String hystrixError() throws Exception{
        return hystrixTestService.hystrixError();
    }

}
