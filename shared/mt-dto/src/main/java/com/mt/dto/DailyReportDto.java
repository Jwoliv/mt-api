package com.mt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class DailyReportDto {
    private BigDecimal earning;
    private BigDecimal spending;
    private Date date;
}
