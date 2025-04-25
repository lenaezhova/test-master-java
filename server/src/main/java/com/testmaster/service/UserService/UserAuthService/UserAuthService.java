package com.testmaster.service.UserService.UserAuthService;

import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.user.JwtTokenPair;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.testmasterapi.domain.user.request.UserLoginRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;

import java.util.List;

/**
 * Интерфейс для работы с пользователями.
 *
 */
public interface UserAuthService {

    /**
     * Зарегистрировать пользователя.
     *
     * @param request Запрос
     */
    JwtTokenPair registration(UserCreateRequest request);

    /**
     * Авторизация.
     *
     * @param request Запрос
     */
    JwtTokenPair login(UserLoginRequest request);

    /**
     * Выйти.
     *
     * @param refreshToken рефреш токен
     */
    void logout(String refreshToken);

    /**
     * Обновить рефреш токен.
     *
     * @param refreshToken рефреш токен
     */
    JwtTokenPair refresh(String refreshToken);

    /**
     * Активировать пользователя.
     *
     * @param link ссылка на активацию
     */
    void activate(String link);
}
