package com.mt.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateAccountRequest {
    private String name;
    private BigDecimal balance;
}
