package com.mt.service.impl;

import com.mt.dto.TransactionDashboardDto;
import com.mt.mapper.TransactionMapper;
import com.mt.repository.TransactionRepository;
import com.mt.repository.view.TransactionDashboardView;
import com.mt.security.UserAuthenticationProvider;
import com.mt.service.TransactionServiceI;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionServiceI {

    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;
    @Setter(onMethod = @__({@Autowired}))
    private UserAuthenticationProvider provider;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionMapper transactionMapper;


    @Override
    public List<TransactionDashboardDto> getTransactionsDashboard(String auth) {
        String email = provider.extractEmail(auth.split(" ")[1]);
        List<TransactionDashboardView> transactionDashboardViews = transactionRepository.getTransactionsDashboard(email, PageRequest.of(0, 3));
        return transactionMapper.viewToDto(transactionDashboardViews);
    }
}
