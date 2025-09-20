package com.manjosh.labs.backend.domain.income;

import com.manjosh.labs.backend.domain.categories.CategoryEntity;
import com.manjosh.labs.backend.domain.profile.ProfileEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_income")
@NamedEntityGraph
public class IncomeEntity {
    @Id
    @SequenceGenerator(name = "ft_id_seq_gen", sequenceName = "ft_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ft_id_seq_gen")
    private Long id;

    private String name;
    private String icon;
    private LocalDate transactionDate;
    private String description;
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.transactionDate == null) {
            this.transactionDate = LocalDate.now();
        }
    }
}
