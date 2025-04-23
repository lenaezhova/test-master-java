package com.testmaster.service.UserService;

import com.testmasterapi.domain.test.request.TestUpdateRequest;
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
public interface UserService {

    /**
     * Метод для получения всех пользователей.
     *
     * @return Все пользователи
     */
    List<UserData> getAll();

    /**
     * Метод для получения всех сессий пользователя.
     *
     * @return Все сессии пользователя
     */
    List<TestSessionData> getAllSessions(Long id);

    /**
     * Метод для получения одного пользователя.
     *
     * @param userId Идентификатор пользователь
     * @return Один пользователь
     */
    UserData getOne(Long userId);

    /**
     * Метод для получения текущего авторизованного пользователя.
     *
     * @param userId Идентификатор пользователь
     * @return Один пользователь
     */
    UserData getCurrent();

    /**
     * Обновить информациб о пользователе.
     *
     * @param userId Идентификатор пользователя
     * @param request Запрос
     */
    void update(Long userId, UserUpdateRequest request);

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
