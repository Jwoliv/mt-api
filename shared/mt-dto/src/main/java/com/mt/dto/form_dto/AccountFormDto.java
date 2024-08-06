package com.mt.dto.form_dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class AccountFormDto {
    private Long id;
    private String name;
}
