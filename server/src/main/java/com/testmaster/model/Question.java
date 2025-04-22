package com.testmaster.model;

import com.testmasterapi.domain.question.QuestionTypes;
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
@Table(name = "questions")
public class Question extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private QuestionTypes type;

    private String title;

    private String description;

    @Column(name = "soft_deleted")
    private boolean softDeleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}