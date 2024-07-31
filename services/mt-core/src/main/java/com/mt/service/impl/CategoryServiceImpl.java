package com.mt.service.impl;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.form_dto.CategoryFormDto;
import com.mt.dto.model_dto.CategoryDto;
import com.mt.mapper.TransactionMapper;
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

    @Override
    public List<CategoryFormDto> getCategories(TypeCategory type) {
        List<Category> categories = categoryRepository.findAllByType(type);
        return categoryMapper.toFormDto(categories);
    }

    @Override
    public List<CategoryDto> getCategories(String authorization) {
        var categories = categoryRepository.findAll();
        return categoryMapper.toDto(categories);
    }

    @Override
    public CategoryPageResponse getCategoryById(String auth, Long id, Integer pageNumber, Integer pageSize) {
        var email = provider.extractEmail(auth);
        var category = categoryRepository.findById(id).orElse(null);
        var transactionsPageable = transactionRepository.getTransactionsPageableByCategoryId(email, PageRequest.of(pageNumber, pageSize), id);
        var transactions = buildPageTransactions(pageNumber, transactionsPageable);
        return CategoryPageResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .transactions(transactions)
                .build();
    }

    private PageElementsResponse<TransactionDashboardDto> buildPageTransactions(Integer pageNumber, Page<Transaction> transactionsPageable) {
        return PageElementsResponse.<TransactionDashboardDto>builder()
                .elements(transactionMapper.mapToDashboardDto(transactionsPageable.getContent()))
                .isPrevPage(pageNumber > 0)
                .isNextPage(transactionsPageable.getTotalPages() > (pageNumber + 1))
                .build();
    }
}
