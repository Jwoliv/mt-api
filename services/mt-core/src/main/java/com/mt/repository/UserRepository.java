package com.mt.repository;

import com.mt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    @Query("""
        SELECT COUNT(u) = 0
        FROM User AS u
        WHERE u.email = :email OR u.username = :username
        """)
    boolean notExistsByEmailOrUsername(@Param("email") String email, @Param("username") String username);
}
