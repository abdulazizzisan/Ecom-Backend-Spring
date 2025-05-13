package com.kolu.ecombackend.category.model;

import com.kolu.ecombackend.category.model.dto.CategoryResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private Set<Product> products = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CategoryResponse toResponse() {
        return CategoryResponse.builder()
                .id(id)
                .name(name)
                .description(description)
                .createdAt(createdAt)
                .build();
    }
}
