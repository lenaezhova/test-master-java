package com.testmaster.controller.TestController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.TestService.TestService;
import com.testmasterapi.api.TestApi.TestApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.TestResultDetailLevel;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.testSession.data.TestSessionResultData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(TestApi.PATH)
public class TestController implements TestApi {
    private final TestService testService;

    @Override
    public PageData<TestData> all(Boolean showOnlyDeleted, TestStatus status, Pageable pageable) {
        return testService.getAll(showOnlyDeleted, status, pageable);
    }

    @Override
    public PageData<TestSessionResultData> results(@PathVariable Long testId, TestResultDetailLevel detailLevel, Pageable pageable) {
        return testService.getPageResults(testId, detailLevel, pageable);
    }

    @Override
    public ResponseEntity<byte[]> resultsExcel(@PathVariable Long testId) {
        try {
            byte[] fileContent = testService.getResultsExcel(testId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("test_results_" + testId + ".xlsx").build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public TestData one(@PathVariable Long testId) {
        return testService.getOne(testId);
    }

    @Override
    public ResponseEntity<Void> create(TestCreateRequest createRequest) {
        var test = testService.create(createRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testId = "testId", checkOwner = true, status = TestStatus.OPENED)
    public void update(Long testId, TestUpdateRequest updateRequest) {
        testService.update(testId, updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testId = "testId", checkOwner = true)
    public void open(Long testId) {
        var data = new TestUpdateRequest();
        data.setStatus(TestStatus.OPENED);
        testService.update(testId, data);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testId = "testId", checkOwner = true)
    public void close(Long testId) {
        var data = new TestUpdateRequest();
        data.setStatus(TestStatus.CLOSED);
        testService.update(testId, data);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testId = "testId", checkOwner = true, status = TestStatus.CLOSED)
    public void delete(Long testId) {
        testService.delete(testId);
    }
}
