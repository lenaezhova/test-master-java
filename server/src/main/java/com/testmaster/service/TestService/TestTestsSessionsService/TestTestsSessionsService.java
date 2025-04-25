package com.testmaster.service.TestService.TestTestsSessionsService;

import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Интерфейс для работы с сесиями теста.
 *
 */
public interface TestTestsSessionsService {

    /**
     * Метод для получения всех сессий теста.
     *
     * @param showUserDeleted Если <b>true</b> в результат будут включены сессии удаленных пользователей
     * @param testId Идентификатор теста
     * @return Страница списка всех сессий тестов
     */
    @NotNull
    PageData<TestSessionData> getAllSessions(Long testId, Boolean showUserDeleted, @NotNull Pageable pageable);

    /**
     * Метод для закрытия всех открытых сессий теста.
     *
     * @param testId Идентификатор теста
     */
    void closeAllOpenedSessions(Long testId);

    /**
     * Метод для удаления всех сессий теста.
     *
     * @param testId Идентификатор теста
     */
    void deleteAllSessions(Long testId);

    /**
     * Метод для создания новой сессии.
     *
     * @param testId Идентификатор теста
     * @param request Запрос
     * @return Созданная теста
     */
    @NotNull
    TestSessionData createSession(Long testId, @NotNull TestSessionCreateRequest request);
}
