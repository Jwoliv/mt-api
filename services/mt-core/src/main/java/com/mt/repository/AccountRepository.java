package com.mt.repository;

import com.mt.dto.AccountDto;
import com.mt.model.transaction.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
        SELECT A FROM Account A
        WHERE A.user.email = :email
    """)
    List<Account> findAccountByEmail(@Param("email") String email);
}
