package com.testmaster.controller.TestController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.TestService.TestService;
import com.testmaster.service.TestService.TestTestsSessionsService.TestTestsSessionsService;
import com.testmasterapi.api.TestApi.TestTestsSessionsApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import com.testmasterapi.domain.testSession.response.TestsSessionsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(TestTestsSessionsApi.PATH)
public class TestTestsSessionsController implements TestTestsSessionsApi {
    private final TestTestsSessionsService testTestsSessionsService;

    @Override
    public PageData<TestSessionData> allSessions(Long testId, Boolean showUserDeleted, Pageable pageable) {
        return testTestsSessionsService.getAllSessions(testId, showUserDeleted, pageable);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeAllOpenedSessions(Long testId) {
        testTestsSessionsService.closeAllOpenedSessions(testId);
    }

    @Override
    @CheckTest(testId = "testId", checkOwner = false, status = TestStatus.OPENED)
    public ResponseEntity<Void> createSession(Long testId, TestSessionCreateRequest request) {
        var testSession = testTestsSessionsService.createSession(testId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
