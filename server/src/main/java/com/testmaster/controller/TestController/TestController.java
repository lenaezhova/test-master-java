package com.testmaster.controller.TestController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.TestService.TestService;
import com.testmasterapi.api.TestApi.TestApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.test.response.TestsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(TestApi.PATH)
public class TestController implements TestApi {
    private final TestService testService;

    @Override
    public PageData<TestData> all(Boolean showDeleted, Pageable pageable) {
        return testService.getAll(showDeleted, pageable);
    }

    @Override
    public TestData one(@PathVariable Long id) {
        return testService.getOne(id);
    }

    @Override
    public ResponseEntity<Void> create(TestCreateRequest createRequest) {
        var test = testService.create(createRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testId = "id", checkOwner = true, status = TestStatus.OPENED)
    public void update(Long id, TestUpdateRequest updateRequest) {
        testService.update(id, updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testId = "id", checkOwner = true)
    public void open(Long id) {
        var data = new TestUpdateRequest();
        data.setStatus(TestStatus.OPENED);
        testService.update(id, data);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testId = "id", checkOwner = true)
    public void close(Long id) {
        var data = new TestUpdateRequest();
        data.setStatus(TestStatus.CLOSED);
        testService.update(id, data);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(testId = "id", checkOwner = true, status = TestStatus.CLOSED)
    public void delete(Long id) {
        testService.delete(id);
    }
}
