package com.mt.model.report;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "daily_report")
public class DailyFinancialReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal dailySpendMoney;
    private BigDecimal dailyEarnMoney;
    private Date date;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
