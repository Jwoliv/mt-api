package com.mt.service.impl;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.dto.model_dto.CategoryDto;
import com.mt.exception.NotFoundException;
import com.mt.mapper.TransactionMapper;
import com.mt.mapper.response.CategoryPageResponseMapper;
import com.mt.model.transaction.Transaction;
import com.mt.repository.TransactionRepository;
import com.mt.response.CategoryPageResponse;
import com.mt.enums.TypeCategory;
import com.mt.mapper.CategoryMapper;
import com.mt.model.transaction.Category;
import com.mt.repository.CategoryRepository;
import com.mt.response.PageElementsResponse;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.CategoryService;
import com.mt.utils.PageElementsResponseBuilder;
import feign.FeignException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Setter(onMethod = @__({@Autowired}))
    private CategoryRepository categoryRepository;
    @Setter(onMethod = @__({@Autowired}))
    private CategoryMapper categoryMapper;
    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionMapper transactionMapper;
    @Setter(onMethod = @__({@Autowired}))
    private CategoryPageResponseMapper categoryPageResponseMapper;
    @Setter(onMethod = @__({@Autowired}))
    private PageElementsResponseBuilder pageElementsResponseBuilder;

    @Override
    public List<CategoryFormDto> getCategories(TypeCategory type) {
        var categories = categoryRepository.findAllByType(type);
        return categoryMapper.mapToCategoryFormDto(categories);
    }

    @Override
    public List<CategoryDto> getCategories(String authorization) {
        var categories = categoryRepository.findAll();
        return categoryMapper.mapToCategoryDto(categories);
    }

    @Override
    public CategoryPageResponse getCategoryById(String auth, Long id, Integer pageNumber, Integer pageSize) {
        var email = provider.extractEmail(auth);
        var category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(Category.class, id));
        var transactionsPageable = transactionRepository.getTransactionsPageableByCategoryId(email, PageRequest.of(pageNumber, pageSize), id);
        var transactions = pageElementsResponseBuilder.buildPageTransactions(pageNumber, transactionsPageable);
        return categoryPageResponseMapper.mapToCategoryPageResponse(category.getId(), category.getName(), transactions);
    }

}
