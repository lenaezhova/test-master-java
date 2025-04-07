package com.testmaster.service.AuthService.user;

import api.domain.user.JwtTokenPair;
import api.domain.user.request.CreateUserRequest;
import api.domain.user.request.LoginRequest;
import api.domain.user.request.RefreshTokenRequest;
import com.testmaster.model.UserModel;

public interface UserService {
    JwtTokenPair registration(CreateUserRequest createUserRequest);

    JwtTokenPair login(LoginRequest loginRequest);

    void logout(RefreshTokenRequest logoutRequest);

    JwtTokenPair refresh(RefreshTokenRequest refreshRequest);

    void activate(String link);

    UserModel getUser(Long id);

    void deleteUser(Long userId);
}
