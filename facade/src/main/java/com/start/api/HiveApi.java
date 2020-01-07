package com.start.api;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.start.api.fallback.HiveApiFallBack;
import com.start.api.fallbackFactory.HiveFallBackFactory;
import com.start.configuration.FeignRequest;
import com.start.configuration.StashErrorDecoder;
import com.start.error.StartException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "HIVE",
        configuration = {StashErrorDecoder.class, FeignRequest.class},
        fallback = HiveApiFallBack.class
//        fallbackFactory = HiveFallBackFactory.class
)
@Component
public interface HiveApi {

    // 默认的hystrix key：Hive#hystrixSuccess()  如果这个方法接收参数比如接收一个String，那么key：Hive#hystrixSuccess(String) 多个参数的以,号分隔
    @RequestMapping("/hystrix/success")
    String hystrixSuccess();

    @RequestMapping("/hystrix/timeout")
    String hystrixTimeOut();

    @RequestMapping("/hystrix/error")
    String hystrixError() throws Exception;

}
