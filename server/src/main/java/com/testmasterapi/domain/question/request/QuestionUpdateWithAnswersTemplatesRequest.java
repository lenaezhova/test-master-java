
package com.testmasterapi.domain.question.request;

import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateQuestionUpdateRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionUpdateWithAnswersTemplatesRequest extends QuestionUpdateRequest {
    private List<AnswerTemplateQuestionUpdateRequest> answerTemplates;
}