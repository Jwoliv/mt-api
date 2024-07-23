package com.mt.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CreatedTransaction {
    private Long id;
    private BigDecimal amount;
    private String categoryName;
    private String accountName;
    private LocalDateTime date;
}
