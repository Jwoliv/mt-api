package com.mt.node;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.dto.model_dto.CategoryDto;
import com.mt.enums.TypeCategory;
import com.mt.response.CategoryPageResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(String auth);
    List<CategoryFormDto> getCategories(String authorization, TypeCategory type);
    CategoryPageResponse getCategoryById(String auth, Long id, Integer pageNumber, Integer pageSize);
}
