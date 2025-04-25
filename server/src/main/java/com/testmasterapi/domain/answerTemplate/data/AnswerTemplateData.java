package com.testmasterapi.domain.answerTemplate.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerTemplateData {
    private Long id;
    private String description;
    private String text;
    private boolean isCorrect;
    private Integer countPoints;
}
