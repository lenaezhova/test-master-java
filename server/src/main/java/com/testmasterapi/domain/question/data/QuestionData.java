package com.testmasterapi.domain.question.data;

import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuestionData {
    private Long id;
    private String title;
    private String description;
    private QuestionTypes type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
