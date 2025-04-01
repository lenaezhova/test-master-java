package com.testmaster.model;

import com.testmaster.model.BaseEntity;
import com.testmaster.model.TestModel.TestModel;
import com.testmaster.model.UserModel.QuestionModel;
import com.testmaster.model.UserModel.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerModel extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionModel question;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestModel test;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column(nullable = false)
    private String value;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

    @Column(name = "count_points")
    private Number countPoints = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}