package com.aws.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class SpringBootIotCoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootIotCoreServiceApplication.class, args);
    }
}