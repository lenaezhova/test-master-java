package com.testmasterapi.domain.answer.data;

import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateResultData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerResultData {
    private Long id;
    private AnswerTemplateResultData answerTemplate;
    private String text;
}
