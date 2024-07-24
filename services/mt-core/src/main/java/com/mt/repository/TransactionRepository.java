package com.mt.repository;

import com.mt.model.DailyAmountReport;
import com.mt.model.transaction.Transaction;
import com.mt.repository.view.DailyReportView;
import com.mt.repository.view.TransactionDashboardView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
        SELECT NEW com.mt.repository.view.DailyReportView(
            SUM(CASE WHEN T.type = com.mt.enums.TypeTransaction.EARNING THEN T.amount END),
            SUM(CASE WHEN T.type = com.mt.enums.TypeTransaction.SPENDING THEN T.amount END),
            T.date
        )
        FROM Transaction T
        WHERE T.user.email = :email
        AND T.date BETWEEN :startOfDay AND :endOfDay
        GROUP BY T.date
        ORDER BY T.date DESC
    """)
    List<DailyReportView> getDailyUserReport(@Param("email") String email,
                                             @Param("startOfDay") LocalDateTime startOfDay,
                                             @Param("endOfDay") LocalDateTime endOfDay);

    @Query("""
        SELECT NEW com.mt.repository.view.TransactionDashboardView(T.id, T.amount, T.type, T.category.name, T.account.name, T.date)
        FROM Transaction T
        WHERE T.user.email = :email
        ORDER BY T.createdAt DESC
    """)
    List<TransactionDashboardView> getTransactionsDashboard(@Param("email") String email, Pageable pageable);


    @Query("""
        SELECT DAR FROM DailyAmountReport DAR
        WHERE DAR.user.email = :email AND DAR.date BETWEEN :startOfDay AND :endOfDay
        ORDER BY DAR.date DESC
    """)
    List<DailyAmountReport> getDailyAmountReports(@Param("email") String email,
                                                  @Param("startOfDay") LocalDateTime startOfDay,
                                                  @Param("endOfDay") LocalDateTime endOfDay,
                                                  Pageable pageable);
}
