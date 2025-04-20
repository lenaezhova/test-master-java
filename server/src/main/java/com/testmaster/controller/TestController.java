package com.testmaster.controller;

import com.testmaster.service.TestService.TestService;
import com.testmasterapi.api.TestApi;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import lombok.RequiredArgsConstructor;
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
    public List<TestData> all() {
        return testService.getAll();
    }

    @Override
    public TestData one(@PathVariable Long id) {
        return testService.getOne(id);
    }

    @Override
    public ResponseEntity<Void> create(TestCreateRequest createRequest) {
        TestData testData = testService.create(createRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Long id, TestUpdateRequest updateRequest) {
        testService.update(id, updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        var request = new TestUpdateRequest();
        request.setDeleted(true);
        testService.update(id, request);
    }
}
