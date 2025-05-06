package com.testmaster.controller.UserController;

import com.testmasterapi.api.UserApi.UserApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserUpdateCurrentRequest;
import com.testmaster.service.UserService.UserService;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserApi.PATH)
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public PageData<UserData> all(Boolean showOnlyDeleted, Pageable pageable) {
        return userService.getAll(showOnlyDeleted, pageable);
    }

    @Override
    public PageData<TestSessionData> allSessions(Long userId,  Boolean showTestDeleted, Pageable pageable) {
        return userService.getAllSessions(userId, showTestDeleted, pageable);
    }

    @Override
    public UserData one(Long userId) {
        return userService.getOne(userId);
    }

    @Override
    public UserData current() {
        return userService.getCurrent();
    }

    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateCurrent(UserUpdateCurrentRequest updateRequest) {
        userService.updateCurrent(updateRequest);
    }

    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(Long userId, UserUpdateRequest request) {
        userService.update(userId, request);
    }
}
