package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;

@Data
public class QuestionCreateRequest {
    private String title;
    private String description;
    private QuestionTypes type;
}
