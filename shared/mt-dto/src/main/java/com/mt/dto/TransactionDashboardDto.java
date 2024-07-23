package com.mt.dto;

import com.mt.enums.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TransactionDashboardDto {
    private Long id;
    private BigDecimal amount;
    private TypeTransaction type;
    private String categoryName;
    private String accountName;
    private LocalDate date;
}