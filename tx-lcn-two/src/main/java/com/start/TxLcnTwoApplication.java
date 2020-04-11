package com.start;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableAsync
@EnableDistributedTransaction
public class TxLcnTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxLcnTwoApplication.class, args);
    }

}