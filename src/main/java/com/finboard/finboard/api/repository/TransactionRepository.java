package com.finboard.finboard.api.repository;

import com.finboard.finboard.api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //generuje automatycznie findAll, findById, save, deleteById, count

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.account.user.id = :userId " +
            "AND (:accountId IS NULL OR t.account.id = :accountId) " +
            "AND (:categoryId IS NULL OR t.category.id = :categoryId) " +
            "AND (:from IS NULL OR t.date >= :from) " +
            "AND (:to IS NULL OR t.date <= :to) " +
            "ORDER BY t.date DESC, t.id DESC")
    List<Transaction> findByUserWithFilters(@Param("userId") Long userId,
                                            @Param("accountId") Long accountId,
                                            @Param("categoryId") Long categoryId,
                                            @Param("from") LocalDate from,
                                            @Param("to") LocalDate to);
}
