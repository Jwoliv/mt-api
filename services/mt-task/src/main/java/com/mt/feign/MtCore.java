package com.mt.feign;

import com.mt.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "${feign.client.config.mt-core-service.name}",
        url="${feign.client.config.mt-core-service.url}",
        configuration = FeignConfig.class
)public interface MtCore {
    @PostMapping("/reports/save/daily-amount-reports")
    void saveDailyAmountReport();
}
