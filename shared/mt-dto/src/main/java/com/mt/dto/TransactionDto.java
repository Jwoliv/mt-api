package com.mt.dto;

import com.mt.enums.TypeTransaction;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class TransactionDto {
    private Long id;
    private LocalDate date;
    private BigDecimal amount;
    private TypeTransaction type;
    private Long userId;
    private Long categoryId;
    private String categoryName;
    private Long accountId;
    private String accountName;
    private Long receiverAccountId;
    private String receiverAccountName;
    private String sender;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
