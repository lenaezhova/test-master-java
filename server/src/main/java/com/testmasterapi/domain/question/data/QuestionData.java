package com.testmasterapi.domain.question.data;

import com.testmasterapi.domain.question.AnswerTemplate;
import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionData {
    private Long id;
    private String title;
    private String description;
    private QuestionTypes type;
    private List<AnswerTemplate> answerTemplates;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
