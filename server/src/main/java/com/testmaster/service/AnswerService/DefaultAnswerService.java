package com.testmaster.service.AnswerService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.model.Answer;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmasterapi.domain.answer.event.AnswerEvent;
import com.testmasterapi.domain.answer.event.AnswerEventsType;
import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultAnswerService implements AnswerService {
    private final AnswerRepository answerRepository;

    private final String notFoundAnswerTemplateMessage = "Ответ не найден";
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void update(Long answerId, AnswerUpdateRequest request) {
        var answer = this.getAnswer(answerId);
        applicationEventPublisher.publishEvent(new AnswerEvent(answer, AnswerEventsType.UPDATE));

        int updated = answerRepository.update(answerId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }

    @Override
    @Transactional
    public void delete(Long answerId) {
        var answer = this.getAnswer(answerId);
        applicationEventPublisher.publishEvent(new AnswerEvent(answer, AnswerEventsType.DELETE));

        int deleted = answerRepository.delete(answerId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }

    private Answer getAnswer(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("Ответ не найден"));
    }
}
