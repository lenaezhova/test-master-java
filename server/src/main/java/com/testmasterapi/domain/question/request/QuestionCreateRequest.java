package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.question.AnswerTemplate;
import com.testmasterapi.domain.question.QuestionTypes;

import java.util.List;

public record QuestionCreateRequest (
    String title,
    String description,
    QuestionTypes type,
    List<AnswerTemplate> answerTemplates
) {
}
