package com.testmaster.controller.TestController;

import com.testmaster.service.TestService.TestGroupService.TestGroupService;
import com.testmaster.service.TestService.TestService;
import com.testmasterapi.api.TestApi.TestApi;
import com.testmasterapi.api.TestApi.TestGroupApi;
import com.testmasterapi.domain.group.request.TestsGroupAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(TestGroupApi.PATH)
public class TestGroupController implements TestGroupApi {
    private final TestGroupService testGroupService;

    @Override
    public ResponseEntity<Void> addTest(Long testId, Long groupId, TestsGroupAddRequest request) {
        testGroupService.addTest(testId, groupId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public void deleteTest(Long testId, Long groupId) {
        testGroupService.deleteTest(testId, groupId);
    }
}
