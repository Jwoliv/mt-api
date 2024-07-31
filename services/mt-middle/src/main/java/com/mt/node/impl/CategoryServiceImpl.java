package com.mt.node.impl;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.dto.model_dto.CategoryDto;
import com.mt.enums.TypeCategory;
import com.mt.feign.CategoryCore;
import com.mt.node.CategoryService;
import com.mt.response.CategoryPageResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Setter(onMethod = @__({@Autowired}))
    private CategoryCore categoryCore;

    @Override
    public List<CategoryDto> getCategories(String auth) {
        return categoryCore.getCategories(auth);
    }

    @Override
    public List<CategoryFormDto> getCategories(String authorization, TypeCategory type) {
        return categoryCore.getCategoriesByEmail(authorization, type);
    }

    @Override
    public CategoryPageResponse getCategoryById(String auth, Long id, Integer pageNumber, Integer pageSize) {
        return categoryCore.getCategoryById(auth, pageNumber, pageSize, id);
    }

}
