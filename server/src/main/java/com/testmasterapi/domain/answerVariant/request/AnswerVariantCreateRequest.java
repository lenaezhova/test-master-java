package com.testmasterapi.domain.answerVariant.request;

public record AnswerVariantCreateRequest(
    String title,
    String description,
    Boolean isCorrect,
    Integer countPoints,
    Long questionId
) {
}
