package com.testmaster.model;

import com.testmasterapi.domain.question.AnswerVariant;
import com.testmasterapi.domain.question.converter.AnswerVariantListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answers")
public class Answer extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "test_session_id")
    private TestSession testSession;

    @Convert(converter = AnswerVariantListConverter.class)
    @Column(name = "answer_variants")
    private List<AnswerVariant> answerVariants;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}