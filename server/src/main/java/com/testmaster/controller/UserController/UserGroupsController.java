package com.testmaster.controller.UserController;

import com.testmaster.service.UserGroupsService.UserGroupsService;
import com.testmasterapi.api.UserApi.UserGroupsApi;
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
@RequestMapping(UserGroupsApi.PATH)
public class UserGroupsController implements UserGroupsApi {
    private final UserGroupsService userGroupsService;

    @Override
    public List<UserGroupsData> all() {
        return userGroupsService.getAll();
    }

    @Override
    public ResponseEntity<Void> add(Long groupId) {
        UserGroupsData userGroupsData = userGroupsService.add(groupId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        userGroupsService.delete(id);
    }
}
