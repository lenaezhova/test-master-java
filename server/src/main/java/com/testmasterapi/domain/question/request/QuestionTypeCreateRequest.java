package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Getter;

@Getter
public class QuestionTypeCreateRequest {
    private String title;
    private QuestionTypes type;
}
