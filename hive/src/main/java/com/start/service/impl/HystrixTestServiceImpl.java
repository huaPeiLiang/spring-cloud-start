package com.start.service.impl;

import com.start.service.HystrixTestService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class HystrixTestServiceImpl implements HystrixTestService {

    @Override
    public void hystrixSuccess(){

    }

    @Override
    public void hystrixTimeOut(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hystrixError() throws Exception{
        throw new IOException("请求异常");
    }

}
