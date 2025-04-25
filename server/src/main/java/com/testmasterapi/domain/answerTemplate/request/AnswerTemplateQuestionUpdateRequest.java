package com.testmasterapi.domain.answerTemplate.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerTemplateQuestionUpdateRequest extends AnswerTemplateUpdateRequest {
    private Long id;
}
