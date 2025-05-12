package com.kolu.ecombackend.category.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@Data
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private Set<Product> products = new HashSet<>();

    @Column(name = "is_active")
    private boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
