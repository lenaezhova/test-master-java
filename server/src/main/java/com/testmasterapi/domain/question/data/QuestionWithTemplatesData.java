package com.testmasterapi.domain.question.data;

import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.question.QuestionTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class QuestionWithTemplatesData extends QuestionData {
    private List<AnswerTemplateData> answerTemplates;
}
