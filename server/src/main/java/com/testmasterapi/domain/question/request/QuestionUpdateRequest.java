package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;
import lombok.Getter;

@Data
public class QuestionUpdateRequest {
    private String title;
    private String description;
    private QuestionTypes savedType;
}
