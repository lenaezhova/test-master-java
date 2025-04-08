package com.testmaster.controller;

import com.testmaster.util.CookieUtil;
import com.testmasterapi.api.UserApi;
import com.testmasterapi.domain.user.JwtTokenPair;
import com.testmasterapi.domain.user.request.CreateUserRequest;
import com.testmasterapi.domain.user.request.LoginRequest;
import com.testmasterapi.domain.user.request.RefreshTokenRequest;
import com.testmasterapi.domain.user.response.TokensResponse;
import com.testmaster.dto.UserDto;
import com.testmaster.mapper.UserMapper;
import com.testmaster.model.UserModel;
import com.testmaster.service.UserService.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserApi.PATH)
public class UserController implements UserApi {
    private final UserMapper userMapper;

    private final UserService userService;

    @Override
    public ResponseEntity<Object> registration(CreateUserRequest request, HttpServletResponse response) {
        JwtTokenPair jwtTokenPair = userService.registration(request);

        CookieUtil.addRefreshTokenCookie(response, jwtTokenPair.refreshToken());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken()));
    }

    @Override
    public ResponseEntity<Object> login(LoginRequest request, HttpServletResponse response) {
        JwtTokenPair jwtTokenPair = userService.login(request);

        CookieUtil.addRefreshTokenCookie(response, jwtTokenPair.refreshToken());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken()));
    }

    @Override
    public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getCookieValue(request, "refreshToken");

        userService.logout(refreshToken);

        CookieUtil.deleteRefreshTokenCookie(response);

        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Object> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getCookieValue(request, "refreshToken");

        JwtTokenPair jwtTokenPair = userService.refresh(refreshToken);

        CookieUtil.addRefreshTokenCookie(response, jwtTokenPair.refreshToken());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken()));

    }

    @Override
    public ResponseEntity<Object> activate(String link) {
        userService.activate(link);

        String clientUrl = System.getenv("CLIENT_URL");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(clientUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<UserDto> getUser(Long id) {
        UserModel user = userService.getUser(id);

        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
