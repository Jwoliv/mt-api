package com.mt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MtTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(MtTaskApplication.class, args);
    }
}