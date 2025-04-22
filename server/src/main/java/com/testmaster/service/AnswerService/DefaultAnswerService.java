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
    private final AnswerMapper answerMapper;

    private final AnswerRepository answerRepository;
    private final TestSessionRepository testSessionRepository;
    private final QuestionRepository questionRepository;
    private final AnswerTemplateRepository answerTemplateRepository;

    private final String notFoundAnswerTemplateMessage = "Ответ не найден";

    @Override
    public List<AnswerData> getAllByQuestionId(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId)
                .stream()
                .map(answerMapper::toData)
                .toList();
    }

    @NotNull
    @Transactional
    @Override
    public AnswerData create(Long testSessionId, Long questionId, Long answerTemplateId, @NotNull AnswerCreateRequest request) {

        var question = this.getQuestion(questionId);
        var testSession = this.getTestSession(testSessionId);
        var answerTemplate = this.getAnswerTemplate(answerTemplateId);

        var entity = answerMapper.toEntity(request, testSession, question, answerTemplate);

        answerRepository.save(entity);

        return answerMapper.toData(entity);
    }

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

    @Override
    @Transactional
    public void deleteAllByQuestionId(Long questionId) {
        int deleted = answerRepository.deleteAllByQuestionId(questionId);
        if (deleted == 0) {
            throw new NotFoundException("Вопрос не найден");
        }
    }

    private TestSession getTestSession(Long testSessionId) {
        return testSessionRepository.findById(testSessionId)
                .orElseThrow(() -> new NotFoundException("Сессия теста не найден"));
    }

    private Question getQuestion(Long questionId) {
        return questionRepository.findQuestionById(questionId)
                .orElseThrow(() -> new NotFoundException("Вопрос не найден"));
    }

    private AnswerTemplate getAnswerTemplate(Long answerTemplateId) {
        return answerTemplateRepository.findById(answerTemplateId)
                .orElseThrow(() -> new NotFoundException("Шаблон ответа не найден"));
    }
}
