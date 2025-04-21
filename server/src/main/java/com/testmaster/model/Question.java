package com.testmaster.model;

import com.testmasterapi.domain.question.AnswerTemplate;
import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.question.converter.AnswerTemplateListConverter;
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
@Table(name = "questions")
public class Question extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private QuestionTypes type;

    private String title;

    private String description;

    @Convert(converter = AnswerTemplateListConverter.class)
    @Column(name = "answer_templates")
    private List<AnswerTemplate> answerTemplates;

    @Column(name = "soft_deleted")
    private boolean softDeleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}