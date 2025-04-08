package com.testmaster.service.AuthService;

import com.testmasterapi.domain.user.JwtTokenPair;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.model.TokenModel;
import com.testmaster.model.UserModel;

import java.time.Duration;
import java.util.Optional;

public interface AuthService {

    String login(String token);

    String createJWT(UserModel user, String secret, Duration expiration);

    JwtTokenPair generateTokens(UserModel user);

    DecodedJWT validateAccessToken(String token);

    DecodedJWT validateRefreshToken(String token);

    void removeToken(String refreshToken);

    Optional<TokenModel> findToken(String refreshToken);

    TokenModel saveToken(UserModel user, String refreshToken);
}