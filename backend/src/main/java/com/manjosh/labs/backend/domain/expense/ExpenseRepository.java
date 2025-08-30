package com.manjosh.labs.backend.domain.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findByProfileIdOrderByTransactionDateDesc(final Long profileId);

    List<ExpenseEntity> findTop5ByProfileIdOrderByTransactionDateDesc(final Long profileId);

    @Query(
            "SELECT SUM(e.amount) FROM ExpenseEntity e WHERE e.profile.id = :profileId AND YEAR(e.transactionDate) = YEAR(CURRENT_DATE) AND MONTH(e.transactionDate) = MONTH(CURRENT_DATE)")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") final Long profileId);

    List<ExpenseEntity> findByProfileIdAndTransactionDateBetweenAndNameContainingIgnoreCase(
            final Long profileId, final LocalDate startDate, final LocalDate endDate, final String keyword, Sort sort);

    List<ExpenseEntity> findByProfileIdAndTransactionDateBetween(
            final Long profileId, final LocalDate startDate, final LocalDate endDate);

    List<ExpenseEntity> findByProfileIdAndTransactionDate(final Long profileId, final LocalDate transactionDate);
}
