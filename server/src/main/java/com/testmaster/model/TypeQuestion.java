package com.testmaster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "type_questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeQuestion extends BaseEntity {
    @Column(nullable = false)
    private String title;
}