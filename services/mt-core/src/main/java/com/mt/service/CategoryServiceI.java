package com.mt.service;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.enums.TypeCategory;

import java.util.List;

public interface CategoryServiceI {
    List<CategoryFormDto> getCategories(TypeCategory type);
}
