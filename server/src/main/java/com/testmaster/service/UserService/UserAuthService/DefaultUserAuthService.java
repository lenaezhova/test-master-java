package com.testmaster.service.UserService.UserAuthService;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.exeption.AuthException;
import com.testmaster.mapper.UserMapper;
import com.testmaster.model.Token;
import com.testmaster.model.User.User;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmaster.service.MailService;
import com.testmaster.service.UserService.UserAuthService.TokenService.TokenService;
import com.testmaster.service.validation.PasswordValidationService;
import com.testmaster.service.validation.UserValidationService;
import com.testmasterapi.domain.user.JwtTokenPair;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.testmasterapi.domain.user.request.UserLoginRequest;
import com.testmasterapi.domain.user.request.UserUpdateCurrentRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.testmaster.exeption.ClientException.UnprocessableEntity;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class DefaultUserAuthService implements UserAuthService {
    protected final UserRepository userRepository;

    private final TokenService tokenService;
    private final MailService mailService;
    protected final UserValidationService userValidationService;
    private final PasswordValidationService passwordValidationService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public JwtTokenPair registration(@Nonnull UserCreateRequest createUserRequest) {
        userValidationService.validate(createUserRequest);
        passwordValidationService.validate(createUserRequest);

        if (userRepository.findByEmail(createUserRequest.email()).isPresent()) {
            throw UnprocessableEntity("Email уже используется!");
        }

        String activationLink = UUID.randomUUID().toString();
        User user = userMapper.toEntity(createUserRequest, activationLink);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        JwtTokenPair jwtTokenPair = tokenService.generateTokens(user);
        tokenService.saveToken(user, jwtTokenPair.refreshToken());

        String link = System.getenv("API_URL") + "/api/users/auth/activate/" + activationLink;
        mailService.sendConfirmEmail(user.getEmail(), link);

        return jwtTokenPair;
    }

    @Override
    @Transactional
    public JwtTokenPair login(@Nonnull UserLoginRequest loginRequest) {
        if (!hasText(loginRequest.email()) || !hasText(loginRequest.password())) {
            throw new AuthException("Имя или пароль не могут быть пустыми!");
        }

        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new AuthException("Неверный email или пароль!"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new AuthException("Неверный email или пароль!");
        }

        if (user.getDeleted()) {
            throw new AuthException("Пользователь с email " + user.getEmail() + " удален!");
        }

        JwtTokenPair jwtTokenPair = tokenService.generateTokens(user);
        tokenService.saveToken(user, jwtTokenPair.refreshToken());

        return jwtTokenPair;
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        User user = this.getUserByRefresh(refreshToken);

        tokenService.removeToken(refreshToken);
    }

    @Override
    @Transactional
    public JwtTokenPair refresh(String refreshToken) {
        User user = this.getUserByRefresh(refreshToken);

        JwtTokenPair jwtTokenPair = tokenService.generateTokens(user);

        tokenService.saveToken(user, jwtTokenPair.refreshToken());

        return jwtTokenPair;
    }

    @Override
    @Transactional
    public void activate(String link) {
        User user = userRepository.findByActivationLink(link)
                .orElseThrow(() -> new AuthException("Неккоректная ссылка активации"));

        var userUpdate = new UserUpdateRequest();
        userUpdate.setIsActivate(true);
        userRepository.update(user.getId(), userUpdate);
    }

    private User getUserByRefresh(String refreshToken) {
        AuthException authException = new AuthException("Пользователь не авторизован!");
        if (!hasText(refreshToken)) {
            throw authException;
        }
        DecodedJWT decodedRefreshJWT = tokenService.validateRefreshToken(refreshToken);
        Token tokenFromDB = tokenService.findToken(refreshToken).orElseThrow(() -> authException);

        if (decodedRefreshJWT == null) {
            throw authException;
        }

        return tokenFromDB.getUser();
    }
}
