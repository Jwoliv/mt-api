package com.mt.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "mt-core", path = "http://localhost:9050/api/v1")
public interface MtCore {

}
