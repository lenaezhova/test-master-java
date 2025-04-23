package com.testmaster.service.TestService.TestQuestionService;

import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с вопросами теста.
 *
 */
public interface TestQuestionService {

    /**
     * Метод для получения всех вопросов теста.
     *
     * @return Все вопросы теста
     */
    List<QuestionData> getAllQuestions(Long testId);

    /**
     * Метод для создания нового вопроса.
     *
     * @param testId Идентификатор теста
     * @param request Запрос
     * @return Созданная теста
     */
    @NotNull
    QuestionData createQuestion(Long testId, @NotNull QuestionCreateRequest request);

    /**
     * Метод для удаления всех вопросов у теста.
     *
     * @param testId Идентификатор теста
     */
    void deleteAllQuestions(Long testId);
}
