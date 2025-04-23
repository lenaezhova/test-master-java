package com.testmaster.controller.TestController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.TestService.TestService;
import com.testmaster.service.TestService.TestTestsSessionsService.TestTestsSessionsService;
import com.testmasterapi.api.TestApi.TestTestsSessionsApi;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(TestTestsSessionsApi.PATH)
public class TestTestsSessionsController implements TestTestsSessionsApi {
    private final TestTestsSessionsService testTestsSessionsService;

    @Override
    public List<TestSessionData> allSessions(Long id) {
        return testTestsSessionsService.getAllSessions(id);
    }

    @Override
    @CheckTest(testId = "id", checkOwner = false, status = TestStatus.OPENED)
    public ResponseEntity<Void> createSession(Long id, TestSessionCreateRequest request) {
        var testSession = testTestsSessionsService.createSession(id, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
