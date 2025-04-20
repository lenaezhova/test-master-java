package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.question.QuestionTypes;

public record QuestionCreateRequest (
    String title,
    String description,
    QuestionTypes savedType,
    Long testId,
    Long typeId
) {
}
