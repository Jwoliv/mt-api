package com.mt.node.impl;

import com.mt.feign.ReportCore;
import com.mt.node.SummaryService;
import com.mt.response.SummaryResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryServiceImpl implements SummaryService {

    @Setter(onMethod = @__({@Autowired}))
    private ReportCore reportCore;

    @Override
    public SummaryResponse getSummaryResponse(String authorization) {
        var dailyReports = reportCore.getDailyAmountReports(authorization);
        var profitReports = reportCore.getProfitReports(authorization);
        return SummaryResponse.builder()
                .dailyReports(dailyReports)
                .profitReports(profitReports)
                .build();
    }
}
