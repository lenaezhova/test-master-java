package com.testmasterapi.domain.answerTemplate.event;

import com.testmaster.model.AnswerTemplate;

public record AnswerTemplateEvent(
        AnswerTemplate answerTemplate,
        AnswerTemplateEventsType eventsType
) {
}