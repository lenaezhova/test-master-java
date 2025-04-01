package com.testmaster.model;

import com.testmaster.model.BaseEntity;
import com.testmaster.model.UserModel.QuestionModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.testmaster.model.UserModel.UserModel;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "answer_variants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVariantModel extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "question_id")
    private QuestionModel question;

    @Column(nullable = false)
    private String text;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "count_points")
    private Number countPoints;
}