package com.testmaster.controller;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.TestSessionService.TestSessionService;
import com.testmasterapi.api.TestSessionApi;
import com.testmasterapi.domain.testSession.request.TestSessionAddAnswerRequest;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionAddTestAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import com.testmasterapi.domain.testSession.response.TestsSessionsResponse;
import lombok.RequiredArgsConstructor;
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
    public TestsSessionsResponse all() {
        return new TestsSessionsResponse(testSessionService.getAll());
    }

    @Override
    public TestSessionData one(Long id) {
        return testSessionService.getOne(id);
    }

    @Override
    @CheckTest(testSessionId = "id", status = TestStatus.OPENED)
    public ResponseEntity<Void> createTestAnswer(Long id, TestSessionAddTestAnswerRequest request) {
        testSessionService.createTestAnswer(id, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @CheckTest(testSessionId = "id", skipCheckStatusForOwner = true, status = TestStatus.OPENED)
    public void updateTestAnswer(Long id, TestSessionAddTestAnswerRequest request) {
        testSessionService.updateTestAnswer(id, request);
    }

    @Override
    @CheckTest(questionId = "questionId", status = TestStatus.OPENED)
    public ResponseEntity<Void> createQuestionAnswer(Long id, Long questionId, TestSessionAddAnswerRequest request) {
        testSessionService.createQuestionAnswer(id, questionId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @CheckTest(questionId = "questionId", skipCheckStatusForOwner = true, status = TestStatus.OPENED)
    public void updateQuestionAnswer(Long id, Long questionId, TestSessionAddAnswerRequest request) {
        testSessionService.updateQuestionAnswer(id, questionId, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "questionId", checkOwner = true, status = TestStatus.OPENED)
    public void update(Long id, TestSessionUpdateRequest request) {
        testSessionService.update(id, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "questionId", status = TestStatus.CLOSED)
    public void delete(Long id) {
        testSessionService.delete(id);
    }
}
