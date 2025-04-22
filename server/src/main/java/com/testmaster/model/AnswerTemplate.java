package com.testmaster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answers_templates")
public class AnswerTemplate extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String description;

    private String text;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "count_points")
    private Integer countPoints;
}