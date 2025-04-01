package com.testmaster.model.UserModel;

import com.testmaster.model.BaseEntity;
import com.testmaster.model.TestModel.TestModel;
import com.testmaster.model.TypeQuestionModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionModel extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "test_id")
    private TestModel test;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeQuestionModel type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}