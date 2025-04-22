package com.testmaster.service.TestSessionService;

import com.testmasterapi.domain.group.data.GroupData;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с сессиями тестов.
 *
 */
public interface TestSessionService {

    /**
     * Метод для получения всех сессий.
     *
     * @return Все сессии
     */
    List<TestSessionData> getAll();

    /**
     * Метод для получения всех сессий пользователя
     *
     * @return Все сессии пользователя
     */
    List<TestSessionData> getAllByUserId(Long userId);

    /**
     * Метод для получения одной сессии.
     *
     * @param testSessionId Идентификатор сессии
     * @return Одна сессия
     */
    TestSessionData getOne(Long testSessionId);

    /**
     * Метод для создания новой сессии.
     *
     * @param request Запрос
     * @return Созданная сессия
     */
    @NotNull
    TestSessionData create(Long testId, @NotNull TestSessionCreateRequest request);

    /**
     * Обновить сессию.
     *
     * @param testSessionId Идентификатор сессии
     * @param request Запрос
     */
    void update(Long testSessionId, TestSessionUpdateRequest request);

    /**
     * Метод для удаления сессии.
     *
     * @param testSessionId Идентификатор сессии
     */
    void delete(Long testSessionId);
}