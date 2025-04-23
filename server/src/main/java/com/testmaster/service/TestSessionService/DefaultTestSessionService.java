package com.testmaster.service.TestSessionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.mapper.TestSessionMapper;
import com.testmaster.model.*;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answer.request.AnswerCreateRequest;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTestSessionService implements TestSessionService {
    private final AnswerMapper answerMapper;
    private final TestSessionMapper testSessionMapper;

    private final TestSessionRepository testSessionRepository;
    private final QuestionRepository questionRepository;
    private final AnswerTemplateRepository answerTemplateRepository;
    private final AnswerRepository answerRepository;

    private final String notFoundTestSessionMessage = "Сессия теста не найдена";

    @Override
    public List<TestSessionData> getAll() {
        return testSessionRepository.findAll()
                .stream()
                .map(testSessionMapper::toData)
                .toList();
    }

    @Override
    public TestSessionData getOne(Long testSessionId) {
        return testSessionMapper.toData(this.getTestSession(testSessionId));
    }

    @NotNull
    @Transactional
    @Override
    public AnswerData createAnswer(
            Long testSessionId,
            Long questionId,
            Long answerTemplateId,
            @NotNull AnswerCreateRequest request
    ) {
        var testSession = this.getTestSession(testSessionId);
        var question = this.getQuestion(questionId);
        var answerTemplate = this.getAnswerTemplate(answerTemplateId);

        var entity = answerMapper.toEntity(request, testSession, question, answerTemplate);

        answerRepository.save(entity);
        return answerMapper.toData(entity);
    }

    @Override
    @Transactional
    public void update(Long testSessionId, TestSessionUpdateRequest request) {
        TestSession session = this.getTestSession(testSessionId);

        if (session.getClosedAt() == null) {
            LocalDateTime now = LocalDateTime.now();
            request.setClosedAt(now);
        }

        int updated = testSessionRepository.update(testSessionId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundTestSessionMessage);
        }
    }

    @Override
    @Transactional
    public void delete(Long testSessionId) {
        int deleted = testSessionRepository.delete(testSessionId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundTestSessionMessage);
        }
    }

    private TestSession getTestSession(Long testSessionId) {
        return testSessionRepository.findById(testSessionId)
                .orElseThrow(() -> new NotFoundException(notFoundTestSessionMessage));
    }

    private Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Вопрос не найден"));
    }

    private AnswerTemplate getAnswerTemplate(Long answerTemplateId) {
        return answerTemplateRepository.findById(answerTemplateId)
                .orElseThrow(() -> new NotFoundException("Шаблон ответа не найден"));
    }
}
