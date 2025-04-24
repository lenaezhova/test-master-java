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
     * @param testId Идентификатор теста
     * @return Все сесии теста
     */
    @NotNull
    PageData<TestSessionData> getAllSessions(Long testId, Boolean showDeleted, @NotNull Pageable pageable);


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
