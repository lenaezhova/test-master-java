package com.testmasterapi.domain.question.data;

import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.answer.data.AnswerResultData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QuestionResultData {
    private Long id;
    private String title;
    private QuestionTypes type;
    private List<AnswerResultData> userAnswers;
}
