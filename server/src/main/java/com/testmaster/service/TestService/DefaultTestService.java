package com.testmaster.service.TestService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.*;
import com.testmaster.model.User.User;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.answer.data.AnswerResultData;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.question.data.QuestionResultData;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.testmaster.repository.TestRepository.TestRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

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
    public PageData<TestSessionResultData> getPageResults(Long testId, TestResultDetailLevel detailLevel, @NotNull Pageable pageable) {
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
    public byte[] getResultsExcel(Long testId) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            var test = this.getTest(testId);
            var testSessions = testSessionRepository.findAllByTestId(testId, false, Pageable.unpaged());

            var allQuestions = questionRepository.findAllByTestId(testId, false);
            List<String> questionHeaders = allQuestions.stream()
                    .map(q -> "Q" + q.getId() + ": " + q.getTitle())
                    .collect(Collectors.toList());

            Sheet sheet = workbook.createSheet("test_results_" + testId);

            Row headerRow = sheet.createRow(0);
            int colIndex = 0;
            headerRow.createCell(colIndex++).setCellValue("User");

            for (String questionHeader : questionHeaders) {
                headerRow.createCell(colIndex++).setCellValue(questionHeader);
            }
            headerRow.createCell(colIndex).setCellValue("Result");

            int rowIndex = 1;
            for (var session : testSessions) {
                var data = testSessionMapper.toResult(session);
                data.setUser(userMapper.toTestSession(session.getUser()));

                List<QuestionResultData> questions = questionRepository.findAllByTestId(testId, false).stream()
                        .map(question -> {
                            var qData = questionMapper.toResult(question);
                            var answers = answerRepository
                                    .findBySessionIdAndQuestionId(session.getId(), question.getId())
                                    .stream()
                                    .map(answerMapper::toResult)
                                    .toList();
                            qData.setUserAnswers(answers);
                            return qData;
                        }).toList();

                data.setQuestions(questions);

                Row row = sheet.createRow(rowIndex++);
                int dataColIndex = 0;

                row.createCell(dataColIndex++).setCellValue(data.getUser().getName());

                Map<Long, String> answersByQuestionId = new HashMap<>();
                for (QuestionResultData question : data.getQuestions()) {
                    Long qId = question.getId();
                    String userAnswer = question.getUserAnswers().stream()
                            .map(AnswerResultData::getText)
                            .collect(Collectors.joining(", "));
                    answersByQuestionId.put(qId, userAnswer);
                }

                for (var question : allQuestions) {
                    String answer = answersByQuestionId.getOrDefault(question.getId(), "");
                    row.createCell(dataColIndex++).setCellValue(answer);
                }

                row.createCell(dataColIndex).setCellValue(data.getCountPoints());
            }

            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new IOException("Error generating Excel report", e);
        }
    }
    @Override
    public TestData getOne(Long id) {
        return testMapper.toData(this.getTest(id));
    }

    @NotNull
    @Transactional
    @Override
    public TestData create(@NotNull TestCreateRequest request) {
        var currentUserDetails = this.getCurrentUserDetails();

        Test test = testMapper.toEntity(request, this.getUser(currentUserDetails.getId()));
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

    private CustomUserDetails getCurrentUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
