package com.manjosh.labs.backend.domain.income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    List<IncomeEntity> findByProfileIdOrderByTransactionDateDesc(final Long profileId);

    List<IncomeEntity> findTop5ByProfileIdOrderByTransactionDateDesc(final Long profileId);

    @Query("SELECT SUM(i.amount) FROM IncomeEntity i WHERE i.profile.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") final Long profileId);

    List<IncomeEntity> findByProfileIdAndTransactionDateBetweenAndNameContainingIgnoreCase(
            final Long profileId, final LocalDate startDate, final LocalDate endDate, final String keyword, Sort sort);

    List<IncomeEntity> findByProfileIdAndTransactionDateBetween(
            final Long profileId, final LocalDate startDate, final LocalDate endDate);
}
