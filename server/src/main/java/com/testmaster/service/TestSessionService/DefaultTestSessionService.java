package com.testmaster.service.TestSessionService;

import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.mapper.TestSessionMapper;
import com.testmaster.model.*;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
import com.testmasterapi.domain.answer.event.AnswerEvent;
import com.testmasterapi.domain.answer.event.AnswerEventsType;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.testSession.TestSessionStatus;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionAddAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionAddTestAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.LongSupplier;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public PageData<TestSessionData> getAll(Boolean showOnlyTestDeleted, @NotNull Pageable pageable) {
        var content = testSessionRepository.findAllTestSessions(showOnlyTestDeleted, pageable)
                .stream()
                .map(testSessionMapper::toData)
                .toList();

        LongSupplier total = () -> testSessionRepository.countAllTestSessions(showOnlyTestDeleted);

        Page<TestSessionData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
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

        var answerEntity = new Answer();
        answerEntity.setTestSession(testSession);
        applicationEventPublisher.publishEvent(new AnswerEvent(answerEntity, AnswerEventsType.CREATE));

        Set<Long> answerIds = request.getAnswerIds();

        // если тип вопроса текстовый, то ищем шаблон ответа,
        // у которого совпадает айди вопроса и тип вопроса текстовый.
        if (question.getType() == QuestionTypes.TEXT && (answerIds == null || answerIds.isEmpty())) {
            var answerTemplate = answerTemplateRepository
                    .findByQuestionIdAndQuestionType(questionId, QuestionTypes.TEXT)
                    .orElseThrow(() -> new NotFoundException(notFoundAnswerTemplateMessage));

            this.checkAnswerTemplateInTestSession(testSession, question);

            var answer = answerMapper.toEntity(request, testSession, question, answerTemplate);
            answerRepository.save(answer);
            return;
        }

        // если тип вопроса с выбором, то ищем все шаблоны ответа с переданными answerIds
        if (QuestionTypes.choiceTypes().contains(question.getType()) && !answerIds.isEmpty()) {
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

            this.checkAnswerTemplateInTestSession(testSession, question);

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
        int updated = testSessionRepository.update(testSessionId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundTestSessionMessage);
        }
    }

    @Override
    @Transactional
    public void close(Long testSessionId) {
        var sessionsAnswers = answerRepository.findAllByTestSessionId(testSessionId);
        AtomicInteger countPoints = new AtomicInteger();

        sessionsAnswers.forEach(answer -> {
            var type = answer.getQuestion().getType();
            var selectedTemplate = answer.getAnswerTemplate();

            // если тип вопроса с выбором, то проверяем выбранные варианты ответов
            // если тип вопроса с текстом, то сравниваем текст в ответе и текст вшаблоне ответа
            if (
                    QuestionTypes.choiceTypes().contains(type) && selectedTemplate.getIsCorrect() ||
                    type == QuestionTypes.TEXT && Objects.equals(answer.getText(), selectedTemplate.getText())
            ) {
                countPoints.addAndGet(selectedTemplate.getCountPoints());
            }
        });
        LocalDateTime now = LocalDateTime.now();

        var update = new TestSessionUpdateRequest();
        update.setStatus(TestSessionStatus.CLOSED);
        update.setCountPoints(countPoints.get());
        update.setClosedAt(now);

        this.update(testSessionId, update);
    }

    @Override
    @Transactional
    public void delete(Long testSessionId) {
        int deleted = testSessionRepository.delete(testSessionId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundTestSessionMessage);
        }
    }

    private void checkAnswerTemplateInTestSession(TestSession testSession, Question question) {
        List<Answer> answer = answerRepository.findBySessionIdAndQuestionId(testSession.getId(), question.getId());
        if (answer != null && !answer.isEmpty()) {
            throw new ClientException("Ответ на вопрос уже создан", HttpStatus.CONFLICT.value());
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
