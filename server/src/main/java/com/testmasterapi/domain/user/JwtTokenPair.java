package com.testmasterapi.domain.user;

public record JwtTokenPair(
        String accessToken,
        String refreshToken
) {}
