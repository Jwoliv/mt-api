package com.mt.dto.reports;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class DailyAmountReportDto {
    private Long index;
    private BigDecimal amount;
    private LocalDateTime date;
    private String color;
}
