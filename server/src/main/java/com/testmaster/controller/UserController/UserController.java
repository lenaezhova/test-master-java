package com.testmaster.controller.UserController;

import com.testmaster.util.CookieUtil;
import com.testmasterapi.api.UserApi.UserApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.response.TestsSessionsResponse;
import com.testmasterapi.domain.user.JwtTokenPair;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.testmasterapi.domain.user.request.UserLoginRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import com.testmasterapi.domain.user.response.TokensResponse;
import com.testmaster.service.UserService.UserService;
import com.testmasterapi.domain.user.response.UsersResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

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
