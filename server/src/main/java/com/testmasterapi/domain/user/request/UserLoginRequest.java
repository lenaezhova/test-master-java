package com.testmasterapi.domain.user.request;

public record UserLoginRequest(
        String email,
        String password
) {}
