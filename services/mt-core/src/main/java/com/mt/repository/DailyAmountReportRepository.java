package com.mt.repository;

import com.mt.model.DailyAmountReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DailyAmountReportRepository extends JpaRepository<DailyAmountReport, Long> {

    @Query("""
        SELECT A.user.id, new com.mt.repository.view.DailyAmountReportView(SUM(A.currentBalance), :date, A.user)
        FROM Account A
        GROUP BY A.user.id, A.user
    """)
    List<Object[]> findSumOfAccountBalancesGroupedByUserId(LocalDateTime date);
}