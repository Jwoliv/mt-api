package com.mt.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewAccountRequest {
    private String name;
    private BigDecimal currentBalance;
}
