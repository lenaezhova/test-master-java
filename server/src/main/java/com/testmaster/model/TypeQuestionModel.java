package com.testmaster.model;

import com.testmaster.model.UserModel.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "type_questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeQuestionModel extends BaseEntity {
    @Column(nullable = false)
    private String title;
}