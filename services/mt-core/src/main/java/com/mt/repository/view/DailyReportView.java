package com.mt.repository.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class DailyReportView {
    private BigDecimal earning;
    private BigDecimal spending;
    private Date date;

    public DailyReportView(BigDecimal earning, BigDecimal spending, Date date) {
        this.earning = earning;
        this.spending = spending;
        this.date = date;
    }
}
