package com.mt.dto.model_dto;

import com.mt.enums.ProfitReportPeriod;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@ToString
public class ProfitReportDto {
    private BigDecimal profit;
    private Long percentage;
    private ProfitReportPeriod period;
}
