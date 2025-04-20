package com.testmaster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "answer_variants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVariant extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String title;

    private String description;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "count_points")
    private Integer countPoints;
}