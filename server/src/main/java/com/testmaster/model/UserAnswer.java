package com.testmaster.model;

import com.testmaster.model.User.User;
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
public class UserAnswer extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "user_value")
    private String userValue;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

    @Column(name = "count_points")
    private Integer countPoints = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}