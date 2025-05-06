package com.testmaster.controller.UserController;

import com.testmaster.service.UserService.UserAuthService.UserAuthService;
import com.testmaster.util.CookieUtil;
import com.testmasterapi.api.UserApi.UserAuthApi;
import com.testmasterapi.domain.user.JwtTokenPair;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.testmasterapi.domain.user.request.UserLoginRequest;
import com.testmasterapi.domain.user.response.TokensResponse;
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
@RequestMapping(UserAuthApi.PATH)
public class UserAuthController implements UserAuthApi {

    private final UserAuthService userAuthService;

    @Override
    public ResponseEntity<Object> registration(UserCreateRequest request, HttpServletResponse response) {
        JwtTokenPair jwtTokenPair = userAuthService.registration(request);

        CookieUtil.setTokensInCookie(response, jwtTokenPair);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken()));
    }

    @Override
    public ResponseEntity<Object> login(UserLoginRequest request, HttpServletResponse response) {
        JwtTokenPair jwtTokenPair = userAuthService.login(request);

        CookieUtil.setTokensInCookie(response, jwtTokenPair);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken()));
    }

    @Override
    public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getRefreshToken(request);

        userAuthService.logout(refreshToken);

        CookieUtil.deleteTokensFromCookie(response);

        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Object> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getRefreshToken(request);

        JwtTokenPair jwtTokenPair = userAuthService.refresh(refreshToken);

        CookieUtil.setTokensInCookie(response, jwtTokenPair);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(new TokensResponse(jwtTokenPair.accessToken()));
    }

    @Override
    public ResponseEntity<Object> activate(String link) {
        userAuthService.activate(link);

        String clientUrl = System.getenv("CLIENT_URL");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(clientUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
