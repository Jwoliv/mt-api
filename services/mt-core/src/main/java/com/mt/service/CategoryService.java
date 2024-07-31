package com.mt.service;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.dto.model_dto.CategoryDto;
import com.mt.response.CategoryPageResponse;
import com.mt.enums.TypeCategory;

import java.util.List;

public interface CategoryService {
    List<CategoryFormDto> getCategories(TypeCategory type);
    List<CategoryDto> getCategories(String authorization);
    CategoryPageResponse getCategoryById(String auth, Long id, Integer pageNumber, Integer pageSize);
}
