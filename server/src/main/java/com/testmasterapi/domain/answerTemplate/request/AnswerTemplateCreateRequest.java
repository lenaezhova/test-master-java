package com.testmasterapi.domain.answerTemplate.request;

public record AnswerTemplateCreateRequest(
        String description,
        String text,
        Boolean isCorrect,
        Integer countPoints
) {
}
