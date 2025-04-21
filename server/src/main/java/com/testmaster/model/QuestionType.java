package com.testmaster.model;

import com.testmasterapi.domain.question.QuestionTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "questions_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionType extends BaseEntity {
    @Column(nullable = false)
    private String title;

    private QuestionTypes type;

    private Boolean deleted;
}