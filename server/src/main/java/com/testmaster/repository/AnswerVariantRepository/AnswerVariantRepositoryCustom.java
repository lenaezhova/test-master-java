package com.testmaster.repository.AnswerVariantRepository;

import com.testmasterapi.domain.answerVariant.request.AnswerVariantUpdateRequest;

public interface AnswerVariantRepositoryCustom {

    int update(Long answerVariantId, AnswerVariantUpdateRequest request);
}