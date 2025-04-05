package com.testmaster.service.AuthService.user;

import api.domain.user.JwtTokenPair;
import api.domain.user.request.CreateUserRequest;
import api.domain.user.request.LoginRequest;
import api.domain.user.request.RefreshRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.exeption.AuthException;
import com.testmaster.model.TokenModel;
import com.testmaster.model.UserModel;
import com.testmaster.repository.UserRepository;
import com.testmaster.service.AuthService.auth.UserAuthService;
import com.testmaster.validation.PasswordValidationService;
import com.testmaster.validation.UserValidationService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.testmaster.exeption.ClientException.UnprocessableEntity;
import static com.testmaster.mapper.UserMapper.map;
import static org.springframework.util.StringUtils.hasText;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    protected final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserAuthService userAuthService;

    protected final UserValidationService userValidationService;

    private final PasswordValidationService passwordValidationService;

    @Override
    public JwtTokenPair registration(@Nonnull CreateUserRequest createUserRequest) {
        userValidationService.validate(createUserRequest);
        passwordValidationService.validate(createUserRequest);

        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw UnprocessableEntity("Email is already in use!");
        }

        UserModel user = map(createUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        JwtTokenPair jwtTokenPair = userAuthService.generateTokens(user);
        userAuthService.saveToken(user, jwtTokenPair.refreshToken());

        return jwtTokenPair;
    }

    @Override
    public JwtTokenPair login(@Nonnull LoginRequest loginRequest) {
        if (!hasText(loginRequest.email()) || !hasText(loginRequest.password())) {
            throw new AuthException("Username or password cannot be empty!");
        }

        UserModel user = userRepository
                .findByEmail(loginRequest.email())
                .orElseThrow(() -> new AuthException("User " + loginRequest.email() + " does not exist!"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new AuthException("Wrong password!");
        }

        if (user.getDeleted()) {
            throw new AuthException("User " + user.getEmail() + " is deleted!");
        }

        JwtTokenPair jwtTokenPair = userAuthService.generateTokens(user);
        userAuthService.saveToken(user, jwtTokenPair.refreshToken());

        return jwtTokenPair;
    }

    @Override
    public JwtTokenPair refresh(RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.refreshToken();
        if (!hasText(refreshToken)) {
            throw new AuthException("User is not authorized!");
        }

        DecodedJWT decodedRefreshJWT = userAuthService.validateRefreshToken(refreshToken);
        Optional<TokenModel> tokenFromDB = userAuthService.findToken(refreshToken);

        if (decodedRefreshJWT == null || tokenFromDB.isEmpty()) {
            throw new AuthException("User is not authorized!");
        }

        UserModel user = tokenFromDB
                .orElseThrow(() -> new AuthException("User is not authorized!"))
                .getUser();

        JwtTokenPair jwtTokenPair = userAuthService.generateTokens(user);

        userAuthService.saveToken(user, jwtTokenPair.refreshToken());

        return jwtTokenPair;
    }

    @Override
    public void deleteUser(@Nonnull Long userId) {
        userRepository.deleteById(userId);
    }
}
