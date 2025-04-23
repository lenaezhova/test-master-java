package com.testmaster.service.AnswerService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultAnswerService implements AnswerService {
    private final AnswerRepository answerRepository;

    private final String notFoundAnswerTemplateMessage = "Ответ не найден";

    @Override
    @Transactional
    public void update(Long answerId, AnswerUpdateRequest request) {
        int updated = answerRepository.update(answerId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }

    @Override
    @Transactional
    public void delete(Long answerId) {
        int deleted = answerRepository.delete(answerId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }
}
