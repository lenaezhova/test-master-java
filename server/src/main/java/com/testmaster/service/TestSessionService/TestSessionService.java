package com.testmaster.service.TestSessionService;

import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionAddAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionAddTestAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Интерфейс для работы с сессиями тестов.
 *
 */
public interface TestSessionService {

    /**
     * Метод для получения всех сессий.
     *
     * @param showOnlyTestDeleted Если <b>true</b> в результате показываются сессии только удаленных тестов
     * @return Все сессии
     */
    PageData<TestSessionData> getAll(Boolean showOnlyTestDeleted, @NotNull Pageable pageable);

    /**
     * Метод для получения одной сессии.
     *
     * @param testSessionId Идентификатор сессии
     * @return Одна сессия
     */
    TestSessionData getOne(Long testSessionId);

    /**
     * Метод для сохранения результата теста.
     * @param testSessionId Идентификатор сессии теста
     * @param request Запрос
     */
    void createTestAnswer(@NotNull Long testSessionId,
                          @NotNull TestSessionAddTestAnswerRequest request);

    /**
     * Метод для обновления результата теста.
     * @param testSessionId Идентификатор сессии теста
     * @param request Запрос
     */
    void updateTestAnswer(@NotNull Long testSessionId,
                          @NotNull TestSessionAddTestAnswerRequest request);

    /**
     * Метод для создание ответа на вопрос.
     * @param testSessionId Идентификатор сессии теста
     * @param questionId Идентификатор вопроса
     * @param request Запрос
     */
    void createQuestionAnswer(@NotNull Long testSessionId,
                      @NotNull Long questionId,
                      @NotNull TestSessionAddAnswerRequest request);

    /**
     * Метод для обновления ответа на вопрос.
     * @param testSessionId Идентификатор сессии теста
     * @param questionId Идентификатор вопроса
     * @param request Запрос
     */
    void updateQuestionAnswer(Long testSessionId,
                      Long questionId,
                      TestSessionAddAnswerRequest request);

    /**
     * Обновить сессию.
     *
     * @param testSessionId Идентификатор сессии
     * @param request Запрос
     */
    void update(Long testSessionId, TestSessionUpdateRequest request);

    /**
     * Закрыть сессию.
     *
     * @param testSessionId Идентификатор сессии
     */
    void close(Long testSessionId);

    /**
     * Метод для удаления сессии.
     *
     * @param testSessionId Идентификатор сессии
     */
    void delete(Long testSessionId);
}