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
    public void hystrixTimeOut(){
        hystrixTestService.hystrixTimeOut();
    }

    @RequestMapping("/error")
    public void hystrixError() throws Exception{
        hystrixTestService.hystrixError();
    }

}
