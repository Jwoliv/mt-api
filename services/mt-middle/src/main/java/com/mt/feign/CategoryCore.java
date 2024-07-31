package com.mt.feign;

import com.mt.config.FeignConfig;
import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.dto.model_dto.CategoryDto;
import com.mt.enums.TypeCategory;
import com.mt.response.CategoryPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "categories",
        url="${mt-middle.endpoints.category}",
        configuration = FeignConfig.class
)
public interface CategoryCore {
    @GetMapping("/form-data")
    List<CategoryFormDto> getCategoriesByEmail(@RequestHeader("Authorization") String authorization,
                                               @RequestParam TypeCategory type);
    @GetMapping
    List<CategoryDto> getCategories(@RequestHeader("Authorization") String auth);

    @GetMapping("/{id}")
    CategoryPageResponse getCategoryById(@RequestHeader("Authorization") String auth,
                                         @RequestParam Integer pageNumber,
                                         @RequestParam Integer pageSize,
                                         @PathVariable Long id
    );
}
