package com.mt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MtMiddleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MtMiddleApplication.class, args);
    }
}
