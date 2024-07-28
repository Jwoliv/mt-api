package com.mt.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateAccountRequest {
    private String name;
    private BigDecimal balance;
}
