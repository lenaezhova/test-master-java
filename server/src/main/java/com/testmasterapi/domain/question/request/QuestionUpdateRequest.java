package com.testmasterapi.domain.question.request;

import lombok.Data;

@Data
public class QuestionUpdateRequest {
    private String title;
    private String description;
    private boolean softDeleted;
}
