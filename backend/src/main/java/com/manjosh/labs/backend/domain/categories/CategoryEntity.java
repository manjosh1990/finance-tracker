package com.manjosh.labs.backend.domain.categories;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tbl_categories")
@Data
@AllArgsConstructor
@NamedEntityGraph
@NoArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @SequenceGenerator(name = "ft_id_seq_gen", sequenceName = "ft_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ft_id_seq_gen")
    private Long id;

    private String name;
    private String type;
    private String icon;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;
}
