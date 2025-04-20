package com.testmaster.service.AuthService;

import com.testmasterapi.domain.user.JwtTokenPair;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.model.Token;
import com.testmaster.model.User;

import java.time.Duration;
import java.util.Optional;

/**
 * Интерфейс для работы с авторизацией.
 *
 */
public interface AuthService {

    /**
     * Метод для создание токена.
     *
     * @param user Пользователь
     * @param secret - Серет для создания токена
     * @param expiration - Время жизни токена
     * @return JWT токен
     */
    String createJWT(User user, String secret, Duration expiration);

    /**
     * Метод для генерации токенов.
     *
     * @param user Пользователь
     * @return Пара токенов access, refresh
     */
    JwtTokenPair generateTokens(User user);

    /**
     * Метод для валидации access токена.
     *
     * @param token access токен
     * @return Раскодированный access токен
     */
    DecodedJWT validateAccessToken(String token);

    /**
     * Метод для валидации refresh токена.
     *
     * @param token refresh токен
     * @return Раскодированный refresh токен
     */
    DecodedJWT validateRefreshToken(String token);

    /**
     * Метод для удаления токена.
     *
     * @param refreshToken refresh токен
     */
    void removeToken(String refreshToken);

    /**
     * Метод для нахождения токена в бд.
     *
     * @param token токен из. бд
     * @return токен
     */
    Optional<Token> findToken(String token);

    /**
     * Метод для сохранения токена.
     *
     * @param user Пользователь
     * @param refreshToken refresh токен
     * @return рефреш токен
     */
    Token saveToken(User user, String refreshToken);
}