package com.testmaster.service.AuthService.auth;

import api.domain.user.JwtTokenPair;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.model.TokenModel;
import com.testmaster.model.UserModel;

import java.time.Duration;
import java.util.Optional;

public interface UserAuthService {

    String createJWT(UserModel user, String secret, Duration expiration);

    JwtTokenPair generateTokens(UserModel user);

    DecodedJWT validateAccessToken(String token);

    DecodedJWT validateRefreshToken(String token);

    void removeToken(String refreshToken);

    Optional<TokenModel> findToken(String refreshToken);

    TokenModel saveToken(UserModel user, String refreshToken);

}