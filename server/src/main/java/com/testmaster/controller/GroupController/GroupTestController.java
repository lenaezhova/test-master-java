package com.testmaster.controller.GroupController;

import com.testmaster.service.GroupTestService.GroupTestService;
import com.testmasterapi.api.GroupApi.GroupTestApi;
import com.testmasterapi.domain.group.request.GroupTestsAddRequest;
import com.testmasterapi.domain.test.data.TestGroupsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(GroupTestApi.PATH)
public class GroupTestController implements GroupTestApi {
    private final GroupTestService groupTestsService;

    @Override
    public List<TestGroupsData> allTestsGroup(Long groupId) {
        return groupTestsService.getAllTestsGroup(groupId);
    }

    @Override
    public ResponseEntity<Void> add(Long groupId, GroupTestsAddRequest request) {
        groupTestsService.add(groupId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long groupId, Long userId) {
        groupTestsService.delete(groupId, userId);
    }
}
