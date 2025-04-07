package com.testmaster.service.AuthService.user;

import com.testmasterapi.domain.user.JwtTokenPair;
import com.testmasterapi.domain.user.request.CreateUserRequest;
import com.testmasterapi.domain.user.request.LoginRequest;
import com.testmasterapi.domain.user.request.RefreshTokenRequest;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.exeption.AuthException;
import com.testmaster.exeption.ClientException;
import com.testmaster.model.TokenModel;
import com.testmaster.model.UserModel;
import com.testmaster.repository.UserRepository;
import com.testmaster.service.AuthService.auth.UserAuthService;
import com.testmaster.service.AuthService.mail.MailService;
import com.testmaster.validation.PasswordValidationService;
import com.testmaster.validation.UserValidationService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.testmaster.exeption.ClientException.UnprocessableEntity;
import static com.testmaster.mapper.UserMapper.map;
import static org.springframework.util.StringUtils.hasText;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    protected final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserAuthService userAuthService;

    private final MailService mailService;

    protected final UserValidationService userValidationService;

    private final PasswordValidationService passwordValidationService;

    @Override
    @Transactional
    public JwtTokenPair registration(@Nonnull CreateUserRequest createUserRequest) {
        userValidationService.validate(createUserRequest);
        passwordValidationService.validate(createUserRequest);

        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw UnprocessableEntity("Email уже используется!");
        }

        String activationLink = UUID.randomUUID().toString();

        UserModel user = map(createUserRequest, activationLink);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        JwtTokenPair jwtTokenPair = userAuthService.generateTokens(user);
        userAuthService.saveToken(user, jwtTokenPair.refreshToken());

        String link = System.getenv("API_URL") + "/api/users/auth/activate/" + activationLink;
        mailService.sendConfirmEmail(user.getEmail(), link);

        return jwtTokenPair;
    }

    @Override
    @Transactional
    public JwtTokenPair login(@Nonnull LoginRequest loginRequest) {
        if (!hasText(loginRequest.email()) || !hasText(loginRequest.password())) {
            throw new AuthException("Имя или пароль не могут быть пустыми!");
        }

        UserModel user = userRepository
                .findByEmail(loginRequest.email())
                .orElseThrow(() -> new AuthException("Неверный email или пароль!"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new AuthException("Неверный email или пароль!");
        }

        if (user.getDeleted()) {
            throw new AuthException("Пользователь с email " + user.getEmail() + " удален!");
        }

        JwtTokenPair jwtTokenPair = userAuthService.generateTokens(user);
        userAuthService.saveToken(user, jwtTokenPair.refreshToken());

        return jwtTokenPair;
    }

    @Override
    @Transactional
    public void logout(RefreshTokenRequest refreshRequest) {
        String refreshToken = refreshRequest.refreshToken();

        UserModel user = this.getUserByRefresh(refreshToken);

        userAuthService.removeToken(refreshToken);
    }

    @Override
    @Transactional
    public JwtTokenPair refresh(RefreshTokenRequest refreshRequest) {
        String refreshToken = refreshRequest.refreshToken();

        UserModel user = this.getUserByRefresh(refreshToken);

        JwtTokenPair jwtTokenPair = userAuthService.generateTokens(user);

        userAuthService.saveToken(user, jwtTokenPair.refreshToken());

        return jwtTokenPair;
    }

    @Override
    public void activate(String link) {
        UserModel user = userRepository
                .findByActivationLink(link)
                .orElseThrow(() -> new AuthException("Неккоректная ссылка активации"));
        user.setIsActivate(true);
        userRepository.save(user);
    }

    @Override
    public UserModel getUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ClientException("Пользователь не найден", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public void deleteUser(@Nonnull Long userId) {
        userRepository.deleteById(userId);
    }

    private UserModel getUserByRefresh(String refreshToken) {
        if (!hasText(refreshToken)) {
            throw new AuthException("Пользователь не авторизован!");
        }

        DecodedJWT decodedRefreshJWT = userAuthService.validateRefreshToken(refreshToken);
        Optional<TokenModel> tokenFromDB = userAuthService.findToken(refreshToken);

        if (decodedRefreshJWT == null || tokenFromDB.isEmpty()) {
            throw new AuthException("Пользователь не авторизован!");
        }

        return tokenFromDB
                .orElseThrow(() -> new AuthException("Пользователь не авторизован!"))
                .getUser();
    }
}
