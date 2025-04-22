package com.testmasterapi.domain.question.data;

import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuestionAnswerData {
    private Long id;
    private String title;
    private String description;
    private boolean softDeleted;
}
