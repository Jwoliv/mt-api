package com.mt.service.impl;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.mapper.CategoryMapper;
import com.mt.model.transaction.Category;
import com.mt.repository.CategoryRepository;
import com.mt.service.CategoryServiceI;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryServiceI {

    @Setter(onMethod = @__({@Autowired}))
    private CategoryRepository categoryRepository;
    @Setter(onMethod = @__({@Autowired}))
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryFormDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDto(categories);
    }
}
