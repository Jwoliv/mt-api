package com.mt.repository.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DailyReportView {
    private BigDecimal earning;
    private BigDecimal spending;
    private LocalDateTime date;

    public DailyReportView(BigDecimal earning, BigDecimal spending, LocalDateTime date) {
        this.earning = earning;
        this.spending = spending;
        this.date = date;
    }
}
