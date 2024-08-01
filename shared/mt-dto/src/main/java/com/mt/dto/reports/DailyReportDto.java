package com.mt.dto.reports;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyReportDto {
    private BigDecimal earning;
    private BigDecimal spending;
    private LocalDate date;
}
