package com.testmaster.repository.AnswerTemplateRepository;

import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;

public interface AnswerTemplateRepositoryCustom {

    int update(Long answerTemplateId, AnswerTemplateUpdateRequest request);
}