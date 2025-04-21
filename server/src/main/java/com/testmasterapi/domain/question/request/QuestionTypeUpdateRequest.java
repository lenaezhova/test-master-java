package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;

@Data
public class QuestionTypeUpdateRequest {
    private String title;
    private QuestionTypes type;
}
