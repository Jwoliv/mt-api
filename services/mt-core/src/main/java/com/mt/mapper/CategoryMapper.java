package com.mt.mapper;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.dto.model_dto.CategoryDto;
import com.mt.model.transaction.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryFormDto> toFormDto(List<Category> categories);
    List<CategoryDto> toDto(List<Category> categories);
}
