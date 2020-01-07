package com.start.api.fallback;

import com.start.api.HiveApi;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HiveApiFallBack implements HiveApi {

    @Override
    public String hystrixSuccess() {
        return null;
    }

    @Override
    public String hystrixTimeOut() {
        return "HiveApiFallBack 超时回退";
    }

    @Override
    public String hystrixError() throws Exception {
        throw new IOException("HiveApiFallBack 回退异常");
    }
}
