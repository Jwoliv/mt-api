package com.mt.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class DailyAmountReportDto {
    private BigDecimal amount;
    private LocalDateTime date;
}
