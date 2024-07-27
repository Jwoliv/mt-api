package com.mt.node;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.enums.TypeCategory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CategoryService {
    List<CategoryFormDto> getCategories(@RequestHeader("Authorization") String authorization,
                                        @RequestParam(value = "type") TypeCategory type);
}
