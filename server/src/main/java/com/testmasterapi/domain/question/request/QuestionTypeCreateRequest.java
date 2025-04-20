package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Getter;

import java.util.Set;

@Getter
public class QuestionTypeCreateRequest {
    private String title;
    private Set<QuestionTypes> types;
}
