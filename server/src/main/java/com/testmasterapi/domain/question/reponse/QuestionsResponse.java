package com.testmasterapi.domain.question.reponse;

import com.testmasterapi.domain.question.data.QuestionData;

import java.util.List;

public record QuestionsResponse (
        List<QuestionData> data
) {

}
