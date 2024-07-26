package com.mt.config;

import feign.Logger;
import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class  FeignConfig {

    @Value("${feign.client.config.mt-core-service.connectTimeout}")
    private Integer connectionTimeout;
    @Value("${feign.client.config.mt-core-service.readTimeout}")
    private Integer readTimeout;

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(connectionTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, true);
    }
}