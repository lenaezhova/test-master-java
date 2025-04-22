package com.testmaster.controller;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.TestSessionService.TestSessionService;
import com.testmasterapi.api.TestSessionApi;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
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
    public List<TestSessionData> all() {
        return testSessionService.getAll();
    }

    @Override
    public List<TestSessionData> allByUserId(Long userId) {
        return testSessionService.getAllByUserId(userId);
    }

    @Override
    public TestSessionData one(Long id) {
        return testSessionService.getOne(id);
    }

    @Override
    @CheckTest(testId = "testId", checkOwner = true, status = TestStatus.OPENED)
    public ResponseEntity<Void> create(Long testId, TestSessionCreateRequest request) {
        testSessionService.create(testId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "id", checkOwner = true, status = TestStatus.OPENED)
    public void update(Long id, TestSessionUpdateRequest request) {
        testSessionService.update(id, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "id", status = TestStatus.CLOSED)
    public void delete(Long id) {
        testSessionService.delete(id);
    }
}
