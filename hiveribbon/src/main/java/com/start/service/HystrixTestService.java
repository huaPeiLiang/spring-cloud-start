package com.start.service;

public interface HystrixTestService {

    String hystrixSuccess();

    void hystrixTimeOut();

    void hystrixError() throws Exception;

}
