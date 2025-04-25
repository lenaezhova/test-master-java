package com.testmaster.controller;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.TestSessionService.TestSessionService;
import com.testmasterapi.api.TestSessionApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.request.TestSessionAddAnswerRequest;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionAddTestAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import com.testmasterapi.domain.testSession.response.TestsSessionsResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(TestSessionApi.PATH)
public class TestSessionController implements TestSessionApi {
    private final TestSessionService testSessionService;

    @Override
    public PageData<TestSessionData> all(Boolean showOnlyTestDeleted, Pageable pageable) {
        return testSessionService.getAll(showOnlyTestDeleted, pageable);
    }

    @Override
    public TestSessionData one(Long testSessionId) {
        return testSessionService.getOne(testSessionId);
    }

    @Override
    @CheckTest(testSessionId = "testSessionId", status = TestStatus.OPENED)
    public ResponseEntity<Void> createTestAnswer(Long testSessionId, TestSessionAddTestAnswerRequest request) {
        testSessionService.createTestAnswer(testSessionId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @CheckTest(testSessionId = "testSessionId", skipCheckStatusForOwner = true, status = TestStatus.OPENED)
    public void updateTestAnswer(Long testSessionId, TestSessionAddTestAnswerRequest request) {
        testSessionService.updateTestAnswer(testSessionId, request);
    }

    @Override
    @CheckTest(questionId = "questionId", skipCheckStatusForOwner = true, status = TestStatus.OPENED)
    public ResponseEntity<Void> createQuestionAnswer(Long testSessionId, Long questionId, TestSessionAddAnswerRequest request) {
        testSessionService.createQuestionAnswer(testSessionId, questionId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @CheckTest(questionId = "questionId", skipCheckStatusForOwner = true, status = TestStatus.OPENED)
    public void updateQuestionAnswer(Long testSessionId, Long questionId, TestSessionAddAnswerRequest request) {
        testSessionService.updateQuestionAnswer(testSessionId, questionId, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testSessionId = "testSessionId", skipCheckStatusForOwner = true, status = TestStatus.OPENED)
    public void update(Long testSessionId, TestSessionUpdateRequest request) {
        testSessionService.update(testSessionId, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testSessionId = "testSessionId", skipCheckStatusForOwner = true, status = TestStatus.OPENED)
    public void close(Long testSessionId) {
        testSessionService.close(testSessionId);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testSessionId = "testSessionId", status = TestStatus.CLOSED)
    public void delete(Long testSessionId) {
        testSessionService.delete(testSessionId);
    }
}
