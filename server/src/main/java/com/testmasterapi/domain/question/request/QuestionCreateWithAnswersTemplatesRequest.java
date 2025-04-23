
package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionCreateWithAnswersTemplatesRequest extends QuestionCreateRequest {
    private List<AnswerTemplateCreateRequest> answerTemplates;
}