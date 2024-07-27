package com.mt.node.impl;

import com.mt.feign.AccountCore;
import com.mt.feign.ReportCore;
import com.mt.feign.TransactionCore;
import com.mt.mapper.DashboardMapper;
import com.mt.node.DashboardService;
import com.mt.response.DashboardResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Setter(onMethod = @__({@Autowired}))
    private AccountCore accountCore;
    @Setter(onMethod = @__({@Autowired}))
    private ReportCore reportCore;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionCore transactionCore;
    @Setter(onMethod = @__({@Autowired}))
    private DashboardMapper dashboardMapper;


    @Override
    public DashboardResponse getDashboardResponse(String authorization) {
        var accounts = accountCore.getAccountsDashboard(authorization);
        var reports = reportCore.getDailyReports(authorization);
        var transactions = transactionCore.getTransactionsDashboard(authorization);
        return dashboardMapper.toDashboardResponse(accounts, reports, transactions);
    }
}
