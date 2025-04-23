package com.testmaster.service.AnswerService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.mapper.AnswerTemplateMapper;
import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import com.testmaster.model.TestSession;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answer.request.AnswerCreateRequest;
import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
