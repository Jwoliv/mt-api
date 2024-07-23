package com.mt.controller;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.service.CategoryServiceI;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Setter(onMethod = @__({@Autowired}))
    private CategoryServiceI categoryService;

    @GetMapping
    public List<CategoryFormDto> getCategoriesByEmail() {
        return categoryService.getCategories();
    }

}
