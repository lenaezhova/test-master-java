package com.testmaster.controller;

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
    public ResponseEntity<Object> registration(CreateUserRequest request) {
        JwtTokenPair jwtTokenPair = userService.registration(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken(), jwtTokenPair.refreshToken()));
    }

    @Override
    public ResponseEntity<Object> login(LoginRequest request) {
        JwtTokenPair jwtTokenPair = userService.login(request);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken(), jwtTokenPair.refreshToken()));
    }

    @Override
    public ResponseEntity<Object> logout(RefreshTokenRequest request) {
        userService.logout(request);

        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Object> refresh(RefreshTokenRequest request, HttpServletResponse response) {
        JwtTokenPair jwtTokenPair = userService.refresh(request);

//        String refreshToken = jwtTokenPair.refreshToken();
//        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
//        refreshCookie.setHttpOnly(true);
//        refreshCookie.setMaxAge(30 * 24 * 60 * 60);
//        refreshCookie.setPath("/");
//        response.addCookie(refreshCookie);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body((new TokensResponse(jwtTokenPair.accessToken(), jwtTokenPair.refreshToken())));
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
