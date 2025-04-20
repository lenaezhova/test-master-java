package com.testmaster.model;

import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.question.converter.QuestionTypeListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "questions_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionType extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Convert(converter = QuestionTypeListConverter.class)
    @Column(columnDefinition = "text[]")
    @Enumerated(EnumType.STRING)
    private Set<QuestionTypes> types;
}