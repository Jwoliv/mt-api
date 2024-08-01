package com.mt.dto.model_dto;

import com.mt.enums.TypeTransaction;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CreatedTransaction {
    private Long id;
    private BigDecimal amount;
    private String categoryName;
    private String accountName;
    private LocalDate date;
    private TypeTransaction type;
}
