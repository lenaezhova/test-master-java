package com.testmaster.service.AuthService;

import com.testmasterapi.domain.user.JwtTokenPair;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.model.Token;
import com.testmaster.model.User.User;

import java.time.Duration;
import java.util.Optional;

public interface AuthService {

    String createJWT(User user, String secret, Duration expiration);

    JwtTokenPair generateTokens(User user);

    DecodedJWT validateAccessToken(String token);

    DecodedJWT validateRefreshToken(String token);

    void removeToken(String refreshToken);

    Optional<Token> findToken(String refreshToken);

    Token saveToken(User user, String refreshToken);
}