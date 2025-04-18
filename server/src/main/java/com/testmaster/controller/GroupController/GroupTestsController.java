package com.testmaster.controller.GroupController;

import com.testmaster.service.GroupTestsService.GroupTestsService;
import com.testmaster.service.UserGroupsService.UserGroupsService;
import com.testmasterapi.api.GroupApi.GroupTestsApi;
import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.request.UserGroupsAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(GroupTestsApi.PATH)
public class GroupTestsController implements GroupTestsApi {
    private final GroupTestsService groupTestsService;

    @Override
    public List<UserGroupsData> all(String groupId) {
        return groupTestsService.getAll(groupId);
    }

    @Override
    public ResponseEntity<Void> add(String groupId, Long testId) {
        UserGroupsData userGroupsData = groupTestsService.add(groupId, testId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public void delete(String groupId, Long testId) {

    }
}
