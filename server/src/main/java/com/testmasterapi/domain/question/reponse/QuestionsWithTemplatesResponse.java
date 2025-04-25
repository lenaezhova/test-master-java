package com.testmasterapi.domain.question.reponse;

import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;

import java.util.List;

public record QuestionsWithTemplatesResponse(
        List<QuestionWithTemplatesData> content
) {

}
