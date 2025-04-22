package com.testmaster.repository.AnswerRepository;

import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;

public interface AnswerRepositoryCustom {

    int update(Long answerId, AnswerUpdateRequest request);
}