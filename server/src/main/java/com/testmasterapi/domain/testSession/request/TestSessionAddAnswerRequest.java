package com.testmasterapi.domain.testSession.request;

import com.testmasterapi.domain.answer.request.AnswerCreateRequest;
import lombok.Data;

import java.util.Set;

@Data
public class TestSessionAddAnswerRequest extends AnswerCreateRequest {
    private Set<Long> answerIds;
}
