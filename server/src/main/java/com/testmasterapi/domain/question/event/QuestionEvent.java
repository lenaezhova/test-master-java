package com.testmasterapi.domain.question.event;

import com.testmaster.model.Question;

public record QuestionEvent(Question question, QuestionEventsType eventsType) {
}