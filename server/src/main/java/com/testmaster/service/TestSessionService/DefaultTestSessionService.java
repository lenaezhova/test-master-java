package com.testmaster.service.TestSessionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.mapper.TestSessionMapper;
import com.testmaster.model.*;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionAddAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionAddTestAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private final String notFoundAnswerTemplateMessage = "Шаблон ответа не найден";

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

    @Override
    public void createTestAnswer(@NotNull Long testSessionId,
                                 @NotNull TestSessionAddTestAnswerRequest request
    ) {
        request.getAnswers().forEach(answer ->
            this.createQuestionAnswer(
                    testSessionId,
                    answer.getQuestionId(),
                    answer.getAnswer()
            )
        );
    }

    @Override
    public void updateTestAnswer(@NotNull Long testSessionId,
                                 @NotNull TestSessionAddTestAnswerRequest request
    ) {
        answerRepository.deleteAllByTestSessionId(testSessionId);
        this.createTestAnswer(testSessionId, request);
    }

    @Override
    public void createQuestionAnswer(@NotNull Long testSessionId,
                             @NotNull Long questionId,
                             @NotNull TestSessionAddAnswerRequest request
    ) {
        var question = this.getQuestion(questionId);
        var testSession = this.getTestSession(testSessionId);
        Set<Long> answerIds = request.getAnswerIds();

        // если тип вопроса текстовый, то ищем шаблон ответа,
        // у которого совпадает айди вопроса и тип вопроса текстовый.
        if (question.getType() == QuestionTypes.TEXT && answerIds.isEmpty()) {
            var answerTemplate = answerTemplateRepository
                    .findByQuestionIdAndQuestionType(questionId, QuestionTypes.TEXT)
                    .orElseThrow(() -> new NotFoundException(notFoundAnswerTemplateMessage));

            var answer = answerMapper.toEntity(request, testSession, question, answerTemplate);
            answerRepository.save(answer);
            return;
        }

        // если тип вопроса с выбором, то ищем все шаблоны ответа с переданными answerIds
        var questionTypesSelected = new ArrayList<>(List.of(
                QuestionTypes.SINGLE, QuestionTypes.MULTIPLE
        ));
        if (questionTypesSelected.contains(question.getType()) && !answerIds.isEmpty()) {
            Map<Long, AnswerTemplate> templateMap = answerTemplateRepository.findAllById(answerIds)
                    .stream()
                    .collect(Collectors.toMap(AnswerTemplate::getId, Function.identity()));

            List<Answer> answers = answerIds.stream()
                    .map(answerId -> {
                        var answerTemplate = templateMap.get(answerId);
                        if (answerTemplate == null) {
                            throw new NotFoundException(notFoundAnswerTemplateMessage);
                        }
                        return answerMapper.toEntity(request, testSession, question, answerTemplate);
                    })
                    .collect(Collectors.toList());

            answerRepository.saveAll(answers);
        }
    }

    @Override
    public void updateQuestionAnswer(@NotNull Long testSessionId,
                                     @NotNull Long questionId,
                                     @NotNull TestSessionAddAnswerRequest request
    ) {
        answerRepository.deleteAllByQuestionIdAndTestSessionId(testSessionId, questionId);
        this.createQuestionAnswer(testSessionId, questionId, request);
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
}
