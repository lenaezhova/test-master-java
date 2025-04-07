package com.testmasterapi.domain.user.request;

public record LoginRequest (
        String email,
        String password
) {}
