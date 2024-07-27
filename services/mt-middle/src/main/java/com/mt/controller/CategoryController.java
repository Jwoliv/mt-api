package com.mt.controller;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.enums.TypeCategory;
import com.mt.node.CategoryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/middle/category")
public class CategoryController {

    @Setter(onMethod = @__({@Autowired}))
    private CategoryService categoryService;

    @GetMapping("/form-data")
    public List<CategoryFormDto> getCategoriesByEmail(@RequestHeader("Authorization") String authorization,
                                                      @RequestParam TypeCategory type) {
        return categoryService.getCategories(authorization, type);
    }
}
