package com.testmasterapi.domain.answer.response;

import com.testmasterapi.domain.answer.data.AnswerData;

import java.util.List;

public record AnswersResponse(
        List<AnswerData> content
) {
}
