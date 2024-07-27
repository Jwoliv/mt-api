package com.mt.feign;

import com.mt.config.FeignConfig;
import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.enums.TypeCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "categories",
        url="${feign.client.config.mt-core-service.path.category}",
        configuration = FeignConfig.class
)
public interface CategoryCore {
    @GetMapping("/form-data")
    List<CategoryFormDto> getCategoriesByEmail(@RequestHeader("Authorization") String authorization,
                                               @RequestParam TypeCategory type);
}
