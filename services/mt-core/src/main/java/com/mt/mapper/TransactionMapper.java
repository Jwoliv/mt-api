package com.mt.mapper;

import com.mt.dto.TransactionDashboardDto;
import com.mt.repository.view.TransactionDashboardView;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    List<TransactionDashboardDto> viewToDto(List<TransactionDashboardView> transactions);
}
