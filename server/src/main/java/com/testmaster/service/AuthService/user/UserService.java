package com.testmaster.service.AuthService.user;

import api.domain.user.JwtTokenPair;
import api.domain.user.request.CreateUserRequest;
import api.domain.user.request.LoginRequest;
import api.domain.user.request.RefreshTokenRequest;

public interface UserService {
    JwtTokenPair registration(CreateUserRequest createUserRequest);

    JwtTokenPair login(LoginRequest loginRequest);

    void logout(RefreshTokenRequest logoutRequest);

    JwtTokenPair refresh(RefreshTokenRequest refreshRequest);

    void deleteUser(Long userId);
}
