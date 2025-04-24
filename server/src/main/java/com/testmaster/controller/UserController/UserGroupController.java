package com.testmaster.controller.UserController;

import com.testmaster.service.UserService.UserGroupService.UserGroupService;
import com.testmasterapi.api.UserApi.UserGroupApi;
import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.UserGroupsAddRequest;
import com.testmasterapi.domain.group.response.GroupsUsersResponse;
import com.testmasterapi.domain.page.data.PageData;
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
@RequestMapping(UserGroupApi.PATH)
public class UserGroupController implements UserGroupApi {
    private final UserGroupService groupUsersService;

    @Override
    public PageData<GroupsUserData> allGroups(Long userId, Pageable pageable) {
        return groupUsersService.getAllGroups(userId, pageable);
    }

    @Override
    public ResponseEntity<Void> addGroup(Long userId, Long groupId, UserGroupsAddRequest request) {
        groupUsersService.addGroup(userId, groupId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(Long userId, Long groupId) {
        groupUsersService.deleteGroup(userId, groupId);
    }
}
