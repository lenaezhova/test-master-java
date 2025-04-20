package com.testmaster.repository.QuestionTypeRepository;

import com.testmasterapi.domain.question.request.QuestionTypeUpdateRequest;

public interface QuestionTypeRepositoryCustom {

    int update(Long typeId, QuestionTypeUpdateRequest request);
}