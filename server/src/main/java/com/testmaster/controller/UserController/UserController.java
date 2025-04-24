package com.testmaster.controller.UserController;

import com.testmasterapi.api.UserApi.UserApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import com.testmaster.service.UserService.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    public PageData<UserData> all(Boolean showDeleted, Pageable pageable) {
        return userService.getAll(showDeleted, pageable);
    }

    @Override
    public PageData<TestSessionData> allSessions(Long id,  Boolean showDeleted, Pageable pageable) {
        return userService.getAllSessions(id, showDeleted, pageable);
    }

    @Override
    public UserData one(Long id) {
        return userService.getOne(id);
    }

    @Override
    public UserData current() {
        return userService.getCurrent();
    }

    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(Long userId, UserUpdateRequest updateRequest) {
        userService.update(userId, updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        var updateRequest = new UserUpdateRequest();
        updateRequest.setDeleted(true);
        userService.update(id, updateRequest);
    }
}
