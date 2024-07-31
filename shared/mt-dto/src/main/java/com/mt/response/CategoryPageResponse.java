package com.mt.response;

import com.mt.dto.TransactionDashboardDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryPageResponse {
    private Long id;
    private String name;
    private PageElementsResponse<TransactionDashboardDto> transactions;
}
