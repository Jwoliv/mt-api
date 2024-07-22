package com.mt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DailyReportDto {
    private BigDecimal earning;
    private BigDecimal spending;
    private LocalDateTime date;
}
