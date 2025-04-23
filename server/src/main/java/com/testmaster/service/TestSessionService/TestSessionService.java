package com.testmaster.service.TestSessionService;

import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionAddAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionAddTestAnswerRequest;
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
     * Метод для удаления сессии.
     *
     * @param testSessionId Идентификатор сессии
     */
    void delete(Long testSessionId);
}