package com.mt.dto.model_dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class CategoryDto {
    private Long id;
    private String name;
}
