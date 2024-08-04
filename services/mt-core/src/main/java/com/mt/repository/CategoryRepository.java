package com.mt.repository;

import com.mt.enums.TypeCategory;
import com.mt.model.transaction.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("SELECT C FROM Category C WHERE C.type = :type")
    List<Category> findAllByType(@Param("type") TypeCategory type);
}
