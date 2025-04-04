package com.testmaster.service.AuthService.user;

import api.domain.user.JwtTokenPair;
import api.domain.user.request.CreateUserRequest;
import api.domain.user.request.LoginRequest;
import api.domain.user.request.RefreshRequest;
import com.testmaster.model.TokenModel;

public interface UserService {
    JwtTokenPair registration(CreateUserRequest createUserRequest);

    JwtTokenPair login(LoginRequest loginRequest);

    TokenModel refresh(RefreshRequest refreshRequest);

    void deleteUser(Long userId);
}
