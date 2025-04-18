package com.testmaster.controller.UserController;

import com.testmaster.util.CookieUtil;
import com.testmasterapi.api.UserApi.UserApi;
import com.testmasterapi.domain.user.JwtTokenPair;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.testmasterapi.domain.user.request.UserLoginRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import com.testmasterapi.domain.user.response.TokensResponse;
import com.testmaster.service.UserService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    public List<UserData> all() {
        return userService.getAll();
    }

    @Override
    public UserData one(Long id) {
        return userService.getOne(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(Long userId, UserUpdateRequest updateRequest) {
        userService.update(userId, updateRequest);
    }

    @Override
    public ResponseEntity<Object> registration(UserCreateRequest request, HttpServletResponse response) {
        JwtTokenPair jwtTokenPair = userService.registration(request);

        CookieUtil.setTokensInCookie(response, jwtTokenPair);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken()));
    }

    @Override
    public ResponseEntity<Object> login(UserLoginRequest request, HttpServletResponse response) {
        JwtTokenPair jwtTokenPair = userService.login(request);

        CookieUtil.setTokensInCookie(response, jwtTokenPair);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken()));
    }

    @Override
    public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getRefreshToken(request);

        userService.logout(refreshToken);

        CookieUtil.deleteTokensFromCookie(response);

        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Object> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getRefreshToken(request);

        JwtTokenPair jwtTokenPair = userService.refresh(refreshToken);

        CookieUtil.setTokensInCookie(response, jwtTokenPair);

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        var updateRequest = new UserUpdateRequest();
        updateRequest.setDeleted(true);
        userService.update(id, updateRequest);
    }
}
