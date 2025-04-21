package com.testmaster.model.User;

import com.testmaster.model.BaseEntity;
import com.testmaster.model.Question;
import com.testmaster.model.Test;
import com.testmasterapi.converter.StringSetConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

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

    @Convert(converter = StringSetConverter.class)
    @Column(name = "answer_variants")
    private Set<String> answerVariants;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

    @Column(name = "count_points")
    private Integer countPoints = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}