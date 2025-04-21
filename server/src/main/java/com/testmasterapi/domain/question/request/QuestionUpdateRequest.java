package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.question.AnswerTemplate;
import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class QuestionUpdateRequest {
    private String title;
    private String description;
    private List<AnswerTemplate> answerTemplates;
    private boolean softDeleted;
}
