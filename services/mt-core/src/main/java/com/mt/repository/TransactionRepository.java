package com.mt.repository;

import com.mt.model.DailyAmountReport;
import com.mt.model.transaction.Transaction;
import com.mt.repository.view.DailyReportView;
import com.mt.request.UpdatedTransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
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
                                             @Param("startOfDay") LocalDate startOfDay,
                                             @Param("endOfDay") LocalDate endOfDay);

    @Query("""
        SELECT T FROM Transaction T
        WHERE T.user.email = :email
        ORDER BY T.createdAt DESC
    """)
    Page<Transaction> getTransactionsPageable(@Param("email") String email, Pageable pageable);


    @Query("""
        SELECT DAR FROM DailyAmountReport DAR
        WHERE DAR.user.email = :email AND DAR.date BETWEEN :startOfDay AND :endOfDay
        ORDER BY DAR.date DESC
    """)
    List<DailyAmountReport> getDailyAmountReports(@Param("email") String email,
                                                  @Param("startOfDay") LocalDate startOfDay,
                                                  @Param("endOfDay") LocalDate endOfDay,
                                                  Pageable pageable);

    @Query("""
        SELECT SUM(CASE WHEN T.type = 'EARNING' THEN T.amount WHEN T.type = 'SPENDING' THEN -T.amount ELSE 0 END)
        FROM Transaction T
        WHERE T.user.email = :email AND T.type <> 'TRANSFER' AND EXTRACT(YEAR FROM T.date) = EXTRACT(YEAR FROM CURRENT_DATE)
    """)
    BigDecimal getYearProfit(String email);

    @Query("""
        SELECT SUM(CASE
                    WHEN T.type = 'EARNING' THEN T.amount
                    WHEN T.type = 'SPENDING' THEN -T.amount
                    ELSE 0
                  END)
        FROM Transaction T
        WHERE T.user.email = :email
          AND T.type <> 'TRANSFER'
          AND EXTRACT(MONTH FROM T.date) = EXTRACT(MONTH FROM CURRENT_DATE)
          AND EXTRACT(YEAR FROM T.date) = EXTRACT(YEAR FROM CURRENT_DATE)
    """)
    BigDecimal getMonthProfit(String email);

    @Query("""
        SELECT SUM(CASE WHEN T.type = 'EARNING' THEN T.amount WHEN T.type = 'SPENDING' THEN -T.amount ELSE 0 END)
        FROM Transaction T
        WHERE T.user.email = :email
          AND T.type <> 'TRANSFER'
          AND EXTRACT(WEEK FROM T.date) = EXTRACT(WEEK FROM CURRENT_DATE)
          AND EXTRACT(YEAR FROM T.date) = EXTRACT(YEAR FROM CURRENT_DATE)
    """)
    BigDecimal getWeekProfit(String email);

    @Query("""
        SELECT SUM(CASE WHEN T.type = 'EARNING' THEN T.amount WHEN T.type = 'SPENDING' THEN -T.amount ELSE 0 END)
        FROM Transaction T
        WHERE T.user.email = :email
          AND T.type <> 'TRANSFER'
          AND DATE_TRUNC('day', T.date) = DATE_TRUNC('day', CURRENT_DATE)
    """)
    BigDecimal getDayProfit(String email);

    @Query("""
        SELECT T FROM Transaction T
        WHERE T.user.email = :email AND T.id = :id
    """)
    Transaction getUserTransactionById(String email, Long id);

    @Modifying
    @Query("DELETE FROM Transaction T WHERE T.account.id = :id")
    void deleteAllByAccountId(Long id);

    @Modifying
    @Query("DELETE FROM Transaction T WHERE T.user.email = :email AND T.id = :id")
    void deleteTransactionById(String email, Long id);

    @Query("""
        SELECT T FROM Transaction T
        WHERE T.account.id = :id AND T.user.email = :email
        ORDER BY T.createdAt DESC
    """)
    Page<Transaction> getTransactionByAccountId(String email, Long id, Pageable pageable);

    @Query("SELECT T FROM Transaction T WHERE T.user.email = :email AND T.category.id = :categoryId")
    Page<Transaction> getTransactionsPageableByCategoryId(String email, PageRequest pageable, Long categoryId);

    @Modifying
    @Query("""
        UPDATE Transaction t
        SET t.date = :#{#request.date},
            t.amount = :#{#request.amount},
            t.type = :#{#request.type},
            t.category.id = :#{#request.categoryId},
            t.account.id = :#{#request.accountId},
            t.receiverAccount.id = :#{#request.receiverAccountId},
            t.sender = :#{#request.sender},
            t.note = :#{#request.note},
            t.updatedAt = :#{#request.updatedAt}
        WHERE t.id = :#{#request.id}
    """)
    void updateTransaction(@Param("request") UpdatedTransactionRequest request);
}
