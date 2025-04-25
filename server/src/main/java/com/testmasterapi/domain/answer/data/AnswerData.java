package com.testmasterapi.domain.answer.data;

import com.testmasterapi.domain.question.data.QuestionAnswerData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AnswerData {
    private Long id;
    private QuestionAnswerData question;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
