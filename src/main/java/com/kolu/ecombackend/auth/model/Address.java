package com.kolu.ecombackend.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address {
    @Id @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String division;
    @Column(nullable = false)
    private String district;
    private String postOffice;
    private String policeStation;
    @Column(nullable = false)
    private String landmark;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
