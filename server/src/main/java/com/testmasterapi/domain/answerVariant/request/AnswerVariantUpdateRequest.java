package com.testmasterapi.domain.answerVariant.request;

import lombok.Data;

@Data
public class AnswerVariantUpdateRequest {
    private String title;
    private String description;
    private Boolean isCorrect;
    private Integer countPoints;
}
