package com.mt.mapper;

import com.mt.dto.CategoryFormDto;
import com.mt.model.transaction.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryFormDto> toDto(List<Category> categories);
}
