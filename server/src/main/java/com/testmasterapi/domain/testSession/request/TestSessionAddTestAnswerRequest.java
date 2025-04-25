package com.testmasterapi.domain.testSession.request;

import lombok.Data;

import java.util.List;

@Data
public class TestSessionAddTestAnswerRequest {
    private List<TestSessionQuestionAnswer> answers;
}