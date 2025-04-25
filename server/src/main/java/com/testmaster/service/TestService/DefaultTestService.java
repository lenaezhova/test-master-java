package com.testmaster.service.TestService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.*;
import com.testmaster.model.User.User;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.TestResultDetailLevel;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.event.TestEvent;
import com.testmasterapi.domain.test.event.TestEventsType;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.testSession.data.TestSessionResultData;
import com.testmasterapi.domain.user.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.testmaster.model.Test.Test;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.testmaster.repository.TestRepository.TestRepository;

import java.util.function.LongSupplier;

@Service
@RequiredArgsConstructor
public class DefaultTestService implements TestService {
    private final TestMapper testMapper;

    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final TestSessionRepository testSessionRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final TestSessionMapper testSessionMapper;
    private final QuestionMapper questionMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final String notFoundTestMessage = "Тест не найден";
    private final AnswerMapper answerMapper;
    private final UserMapper userMapper;

    @NotNull
    @Override
    public PageData<TestData> getAll(Boolean showOnlyDeleted, TestStatus status, @NotNull Pageable pageable) {
        var content = testRepository.findAllTests(showOnlyDeleted, status, pageable)
                .stream()
                .map(testMapper::toData)
                .toList();

        LongSupplier total = () -> testRepository.countAllTests(showOnlyDeleted, status);

        Page<TestData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @NotNull
    @Override
    public PageData<TestSessionResultData> getResults(Long testId, TestResultDetailLevel detailLevel, @NotNull Pageable pageable) {
        var testSessions = testSessionRepository.findAllByTestId(testId, false, pageable);

        LongSupplier total = () -> testSessionRepository.countAllByTestId(testId, false);

        var content = testSessions.stream()
                .map(session -> {
                    var data = testSessionMapper.toResult(session);
                    data.setUser(userMapper.toTestSession(session.getUser()));

                    if (detailLevel == TestResultDetailLevel.FULL) {
                        var questions = questionRepository.findAllByTestId(testId, false).stream()
                                .map(question -> {
                                    var qData = questionMapper.toResult(question);

                                    var answers = answerRepository
                                            .findBySessionIdAndQuestionId(session.getId(), question.getId())
                                            .stream()
                                            .map(answerMapper::toResult)
                                            .toList();

                                    qData.setUserAnswers(answers);
                                    return qData;
                                })
                                .toList();

                        data.setQuestions(questions);
                    }

                    return data;
                })
                .toList();

        Page<TestSessionResultData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @Override
    public TestData getOne(Long id) {
        return testMapper.toData(this.getTest(id));
    }

    @NotNull
    @Transactional
    @Override
    public TestData create(@NotNull TestCreateRequest request) {
        var currentUser = getCurrentUser();

        Test test = testMapper.toEntity(request, currentUser);
        testRepository.save(test);

        return testMapper.toData(test);
    }

    @Override
    @Transactional
    public void update(Long testId, TestUpdateRequest updateRequest) {
        if (updateRequest.getStatus() == TestStatus.CLOSED) {
            var test = this.getTest(testId);
            applicationEventPublisher.publishEvent(new TestEvent(test, TestEventsType.CLOSE));
        }

        int updated = testRepository.update(testId, updateRequest);
        if (updated == 0) {
            throw new NotFoundException(notFoundTestMessage);
        }
    }

    @Override
    @Transactional
    public void delete(Long testId) {
        var test = this.getTest(testId);
        applicationEventPublisher.publishEvent(new TestEvent(test, TestEventsType.DELETE));

        var updateRequest = new TestUpdateRequest();
        updateRequest.setDeleted(true);
        updateRequest.setStatus(TestStatus.CLOSED);
        int updated = testRepository.update(testId, updateRequest);

        if (updated == 0) {
            throw new NotFoundException(notFoundTestMessage);
        }
    }

    private Test getTest(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException(notFoundTestMessage));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private User getCurrentUser() {
        var customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.getUser(customUserDetails.getId());
    }

}
