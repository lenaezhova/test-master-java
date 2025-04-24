package com.testmaster.service.UserService;

import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.user.JwtTokenPair;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.testmasterapi.domain.user.request.UserLoginRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Интерфейс для работы с пользователями.
 *
 */
public interface UserService {

    /**
     * Метод для получения всех пользователей.
     *
     * @param showOnlyDeleted Если <b>true</b> в результате показываются только удаленные пользователи
     * @return Страница списка пользователей
     */
    @NotNull
    PageData<UserData> getAll(Boolean showOnlyDeleted, @NotNull Pageable pageable);

    /**
     * Метод для получения всех сессий пользователя.
     *
     * @param showTestDeleted Если <b>true</b> в результат включаются сессии удаленных тестов
     * @return Страница тестовых сессий пользователей
     */
    @NotNull
    PageData<TestSessionData> getAllSessions(Long id, Boolean showTestDeleted, @NotNull Pageable pageable);

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
}
