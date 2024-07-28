package com.mt.repository;

import com.mt.model.transaction.Account;
import com.mt.request.UpdateAccountRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
        SELECT A FROM Account A
        WHERE A.user.email = :email
        ORDER BY A.currentBalance DESC
    """)
    List<Account> findAccountsByEmail(@Param("email") String email, Pageable pageable);

    @Query("""
        SELECT A FROM Account A
        WHERE A.user.email = :email
        ORDER BY A.currentBalance DESC
    """)
    List<Account> findAccountsByEmail(@Param("email") String email);

    @Query("""
        SELECT A FROM Account AS A
        WHERE A.user.email = :email
        ORDER BY A.currentBalance DESC
        LIMIT 3
    """)
    List<Account> getAccountsDashboard(String email);

    Optional<Account> findByName(String name);

    @Query("""
        SELECT A FROM Account A
        WHERE A.user.email = :email AND A.id = :id
        ORDER BY A.currentBalance DESC
    """)
    Optional<Account> getUserAccountById(String email, Long id);

    @Modifying
    @Query("DELETE FROM Account A WHERE A.user.email = :email AND A.id = :id")
    void deleteAccountById(String email, Long id);

    @Modifying
    @Query("""
        UPDATE Account A
        SET A.currentBalance = :#{#request.balance}, A.name = :#{#request.name}
        WHERE A.user.email = :email AND A.id = :id
    """)
    void updateAccountById(String email, Long id, UpdateAccountRequest request);
}
