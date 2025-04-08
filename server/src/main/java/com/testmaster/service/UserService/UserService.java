package com.testmaster.service.UserService;

import com.testmasterapi.domain.user.JwtTokenPair;
import com.testmasterapi.domain.user.request.CreateUserRequest;
import com.testmasterapi.domain.user.request.LoginRequest;
import com.testmasterapi.domain.user.request.RefreshTokenRequest;
import com.testmaster.model.UserModel;

public interface UserService {
    JwtTokenPair registration(CreateUserRequest createUserRequest);

    JwtTokenPair login(LoginRequest loginRequest);

    void logout(String refreshToken);

    JwtTokenPair refresh(String refreshToken);

    void activate(String link);

    UserModel getUser(Long id);

    void deleteUser(Long userId);
}
