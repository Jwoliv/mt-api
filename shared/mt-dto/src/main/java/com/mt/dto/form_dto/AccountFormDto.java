package com.mt.dto.form_dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountFormDto {
    private Long id;
    private String name;
}