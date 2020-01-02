package com.start.api;

import com.alibaba.fastjson.JSONObject;
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

@FeignClient(name = "HIVE", configuration = {StashErrorDecoder.class, FeignRequest.class})
@Component
public interface HiveApi {

    @RequestMapping("/hystrix/success")
    void hystrixSuccess();

    @RequestMapping("/hystrix/timeout")
    void hystrixTimeOut();

    @RequestMapping("/hystrix/error")
    void hystrixError() throws Exception;

}
