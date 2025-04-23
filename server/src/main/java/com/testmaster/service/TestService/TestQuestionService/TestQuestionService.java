package com.testmaster.service.TestService.TestQuestionService;

import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionCreateWithAnswersTemplatesRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
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
     * @return Созданный вопрос
     */
    @NotNull
    QuestionData createQuestion(Long testId, @NotNull QuestionCreateRequest request);

    /**
     * Метод для создания нового вопроса с шаблонами ответов.
     *
     * @param testId Идентификатор теста
     * @param request Запрос
     * @return Созданный вопрос
     */
    @NotNull
    QuestionData createQuestionWithTemplates(Long testId, @NotNull QuestionCreateWithAnswersTemplatesRequest request);

    /**
     * Метод для удаления всех вопросов у теста.
     *
     * @param testId Идентификатор теста
     */
    void deleteAllQuestions(Long testId);
}
