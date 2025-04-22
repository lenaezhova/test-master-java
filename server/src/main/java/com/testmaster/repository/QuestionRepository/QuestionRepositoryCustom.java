package com.testmaster.repository.QuestionRepository;

import com.testmasterapi.domain.question.request.QuestionUpdateRequest;

public interface QuestionRepositoryCustom {

    int update(Long questionId, QuestionUpdateRequest request);
}