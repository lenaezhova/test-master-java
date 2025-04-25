package com.testmasterapi.domain.answer.event;

import com.testmaster.model.Answer;
import jakarta.annotation.Nullable;

public record AnswerEvent(
        Answer answer,
        AnswerEventsType eventsType
) {
}