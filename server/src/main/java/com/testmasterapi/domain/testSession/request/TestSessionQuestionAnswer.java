package com.testmasterapi.domain.testSession.request;

import lombok.Data;

@Data
public class TestSessionQuestionAnswer {
    private Long questionId;
    private TestSessionAddAnswerRequest answer;
}
