package com.testmaster.controller;

import api.api.UserApi;
import api.domain.user.JwtTokenPair;
import api.domain.user.request.CreateUserRequest;
import api.domain.user.request.LoginRequest;
import api.domain.user.request.RefreshRequest;
import api.domain.user.response.LoginResponse;
import api.domain.user.response.RefreshResponse;
import com.testmaster.model.TokenModel;
import com.testmaster.model.UserModel;
import com.testmaster.service.AuthService.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserApi.PATH)
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public ResponseEntity<Object> registration(CreateUserRequest request) {
        JwtTokenPair jwtTokenPair = userService.registration(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public ResponseEntity<Object> login(LoginRequest request) {
        JwtTokenPair jwtTokenPair = userService.login(request);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new LoginResponse(jwtTokenPair.accessToken()));
    }

    @Override
    public ResponseEntity<Object> refresh(RefreshRequest request, HttpServletResponse response) {
        TokenModel tokenModel = userService.refresh(request);

        Cookie refreshCookie = new Cookie("refreshToken", tokenModel.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(30 * 24 * 60 * 60);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new RefreshResponse(tokenModel.getRefreshToken()));
    }
}
