package com.start.api.fallbackFactory;

import com.start.api.HiveApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HiveFallBackFactory implements FallbackFactory<HiveApi> {

    @Override
    public HiveApi create(Throwable throwable) {
        return new HiveApi() {
            @Override
            public String hystrixSuccess() {
                return null;
            }

            @Override
            public String hystrixTimeOut() {
                return "HiveFallBackFactory 超时回退";
            }

            @Override
            public String hystrixError() throws Exception {
                throw new IOException("HiveFallBackFactory 回退异常");
            }
        };
    }
}
