package com.testmasterapi.domain.answerTemplate.request;

import lombok.Data;

@Data
public class AnswerTemplateUpdateRequest {
    private String description;
    private String text;
    private Boolean isCorrect;
    private Integer countPoints;
}
