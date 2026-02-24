package com.finboard.finboard.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    String icon;

    String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CategoryType type;
}
