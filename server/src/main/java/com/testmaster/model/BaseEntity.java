package com.testmaster.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}