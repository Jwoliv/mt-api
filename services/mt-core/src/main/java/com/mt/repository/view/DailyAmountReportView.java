package com.mt.repository.view;

import com.mt.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
