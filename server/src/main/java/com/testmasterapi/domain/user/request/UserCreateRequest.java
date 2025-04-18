package com.testmasterapi.domain.user.request;

public record UserCreateRequest(
        String password,
        String name,
        String email
) {
}
