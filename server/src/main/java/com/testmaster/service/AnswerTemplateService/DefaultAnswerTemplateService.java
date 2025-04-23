package com.testmaster.service.AnswerTemplateService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultAnswerTemplateService implements AnswerTemplateService {
    private final AnswerTemplateRepository answerTemplateRepository;

    private final String notFoundAnswerTemplateMessage = "Шаблон ответа не найден";

    @Override
    @Transactional
    public void update(Long answerTemplateId, AnswerTemplateUpdateRequest request) {
        int updated = answerTemplateRepository.update(answerTemplateId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }

    @Override
    @Transactional
    public void delete(Long answerTemplateId) {
        int deleted = answerTemplateRepository.delete(answerTemplateId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }
}
