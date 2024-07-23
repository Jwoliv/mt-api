package com.mt.service;

import com.mt.dto.form_dto.CategoryFormDto;

import java.util.List;

public interface CategoryServiceI {
    List<CategoryFormDto> getCategories();
}
