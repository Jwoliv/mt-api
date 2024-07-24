package com.mt.repository.view;

import com.mt.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class DailyAmountReportView {
    private BigDecimal amount;
    private LocalDateTime date;
    private User user;
}
