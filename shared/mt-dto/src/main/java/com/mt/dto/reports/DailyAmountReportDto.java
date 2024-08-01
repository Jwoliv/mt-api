package com.mt.dto.reports;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class DailyAmountReportDto {
    private Long index;
    private BigDecimal amount;
    private LocalDate date;
    private String color;
}
