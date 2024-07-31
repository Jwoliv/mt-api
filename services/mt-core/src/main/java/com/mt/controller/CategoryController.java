package com.mt.controller;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.dto.model_dto.CategoryDto;
import com.mt.response.CategoryPageResponse;
import com.mt.enums.TypeCategory;
import com.mt.service.CategoryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Setter(onMethod = @__({@Autowired}))
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestHeader("Authorization") String authorization) {
        return categoryService.getCategories(authorization);
    }

    @GetMapping("/{id}")
    public CategoryPageResponse getCategoryById(@RequestHeader("Authorization") String auth,
                                                @RequestParam Integer pageNumber,
                                                @RequestParam Integer pageSize,
                                                @PathVariable Long id
    ) {
        return categoryService.getCategoryById(auth, id, pageNumber, pageSize);
    }


    @GetMapping("/form-data")
    public List<CategoryFormDto> getCategoriesByEmail(@RequestParam TypeCategory type) {
        return categoryService.getCategories(type);
    }

}
