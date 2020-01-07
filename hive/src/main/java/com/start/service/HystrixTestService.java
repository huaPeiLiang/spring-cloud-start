package com.start.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface HystrixTestService {

    String hystrixSuccess();

    String hystrixTimeOut();

    String hystrixError() throws Exception;

}
