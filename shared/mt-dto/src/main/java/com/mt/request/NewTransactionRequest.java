package com.mt.request;

import com.mt.enums.TypeTransaction;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
public class NewTransactionRequest {
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Long accountId;
    @NotNull
    private Long categoryId;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private TypeTransaction type;
    private String sender;
    private Long senderAccount;
    private Long receiverAccount;
    private String note;
}
