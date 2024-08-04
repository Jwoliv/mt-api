package com.mt.mapper.response;

import com.mt.dto.TransactionDashboardDto;
import com.mt.response.CategoryPageResponse;
import com.mt.response.PageElementsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryPageResponseMapper {
    CategoryPageResponse mapToCategoryPageResponse(Long id,
                                                   String name,
                                                   PageElementsResponse<TransactionDashboardDto> transactions
    );
}
