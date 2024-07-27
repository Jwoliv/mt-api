package com.mt.node.impl;

import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.enums.TypeCategory;
import com.mt.feign.CategoryCore;
import com.mt.node.CategoryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Setter(onMethod = @__({@Autowired}))
    private CategoryCore categoryCore;

    @Override
    public List<CategoryFormDto> getCategories(String authorization, TypeCategory type) {
        return categoryCore.getCategoriesByEmail(authorization, type);
    }
}
