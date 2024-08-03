package com.mt.request;

import com.mt.enums.TypeTransaction;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ToString
public class ChangeTransactionRequest {
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Long accountId;
    @NotNull
    private Long categoryId;
    @NotNull
    private LocalDate date;
    @NotNull
    private TypeTransaction type;
    private String sender;
    private Long senderAccountId;
    private Long receiverAccountId;
    private String note;
}
