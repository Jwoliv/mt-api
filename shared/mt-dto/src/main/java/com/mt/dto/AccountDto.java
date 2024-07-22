package com.mt.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountDto {
    private Long id;
    private String name;
    private BigDecimal spendMoney;
    private BigDecimal earnMoney;
    private BigDecimal currentBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
