package com.start.controller.hive;

import com.start.api.HiveApi;
import com.start.error.StartException;
import com.start.pojo.ReturnData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/hystrix")
public class HystrixController {

    @Resource
    private HiveApi hiveApi;

    @RequestMapping("/success")
    public ReturnData hystrixSuccess(){
        try {
            return ReturnData.success( hiveApi.hystrixSuccess() );
        } catch (Exception e) {
            return ReturnData.failed(e.getMessage());
        }
    }

    @RequestMapping("/timeout")
    public ReturnData hystrixTimeOut(){
        try {
            return ReturnData.success( hiveApi.hystrixTimeOut() );
        } catch (Exception e) {
            return ReturnData.failed(e.getMessage());
        }
    }

    @RequestMapping("/error")
    public ReturnData hystrixError() throws Exception{
        try {
            return ReturnData.success( hiveApi.hystrixError() );
        } catch (Exception e) {
            return ReturnData.failed(e.getMessage());
        }
    }

}
