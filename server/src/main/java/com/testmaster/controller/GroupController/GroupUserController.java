package com.testmaster.controller.GroupController;

import com.testmaster.service.GroupUserService.GroupUserService;
import com.testmasterapi.api.GroupApi.GroupUserApi;
import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.GroupUsersAddRequest;
import com.testmasterapi.domain.user.data.UserGroupsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(GroupUserApi.PATH)
public class GroupUserController implements GroupUserApi {
    private final GroupUserService groupUsersService;

    @Override
    public List<UserGroupsData> allUsersGroup(Long groupId) {
        return groupUsersService.getAllUsersGroup(groupId);
    }

    @Override
    public List<GroupsUserData> allGroupsUser(Long userId) {
        return groupUsersService.getAllGroupsUser(userId);
    }

    @Override
    public ResponseEntity<Void> add(Long groupId, GroupUsersAddRequest request) {
        groupUsersService.add(groupId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long groupId, Long userId) {
        groupUsersService.delete(groupId, userId);
    }
}
