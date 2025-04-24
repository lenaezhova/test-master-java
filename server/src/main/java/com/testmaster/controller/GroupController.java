package com.testmaster.controller;

import com.testmasterapi.domain.group.data.GroupData;
import com.testmaster.service.GroupService.GroupService;
import com.testmasterapi.api.GroupApi.GroupApi;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import com.testmasterapi.domain.group.response.GroupsResponse;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.data.TestGroupsData;
import com.testmasterapi.domain.test.response.TestsGroupsResponse;
import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.response.UsersGroupsResponse;
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
@RequestMapping(GroupApi.PATH)
public class GroupController implements GroupApi {
    private final GroupService groupService;

    @Override
    public PageData<GroupData> all(Pageable pageable) {
        return groupService.getAll(pageable);
    }

    @Override
    public PageData<UserGroupsData> allUsers(Long id, Boolean showDeleted, Pageable pageable) {
        return groupService.getAllUsers(id, showDeleted, pageable);
    }

    @Override
    public PageData<TestGroupsData> allTests(Long id, Boolean showDeleted, Pageable pageable) {
        return groupService.getAllTests(id, showDeleted, pageable);
    }

    @Override
    public GroupData one(Long id) {
        return groupService.getOne(id);
    }

    @Override
    public ResponseEntity<Void> create(GroupCreateRequest request) {
        groupService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Long id, GroupUpdateRequest updateRequest) {
        groupService.update(id, updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        groupService.delete(id);
    }
}
