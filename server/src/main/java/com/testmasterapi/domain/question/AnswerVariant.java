package com.testmasterapi.domain.question;

import lombok.Data;

@Data
public class AnswerVariant {
    private String text;
    private boolean isCorrect;
    private Integer countPoints;
}
