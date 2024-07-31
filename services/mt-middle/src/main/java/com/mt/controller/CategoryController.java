package com.mt.controller;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.dto.model_dto.CategoryDto;
import com.mt.enums.TypeCategory;
import com.mt.node.CategoryService;
import com.mt.response.CategoryPageResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/middle/category")
public class CategoryController {

    @Setter(onMethod = @__({@Autowired}))
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestHeader("Authorization") String auth) {
        return categoryService.getCategories(auth);
    }

    @GetMapping("/{id}")
    public CategoryPageResponse getCategoryById(@RequestHeader("Authorization") String auth,
                                                @RequestParam(defaultValue = "0") Integer pageNumber,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                @PathVariable Long id
    ) {
        return categoryService.getCategoryById(auth, id, pageNumber, pageSize);
    }

    @GetMapping("/form-data")
    public List<CategoryFormDto> getCategoriesByEmail(@RequestHeader("Authorization") String authorization,
                                                      @RequestParam TypeCategory type) {
        return categoryService.getCategories(authorization, type);
    }
}
