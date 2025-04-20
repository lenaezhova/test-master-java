package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;

import java.util.Set;

@Data
public class QuestionTypeUpdateRequest {
    private String title;
    private Set<QuestionTypes> types;
}
