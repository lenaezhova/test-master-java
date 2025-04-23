package com.testmaster.service.QuestionService;

import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;

import java.util.List;

/**
 * Интерфейс для работы с вопросами.
 *
 */
public interface QuestionService {

    /**
     * Метод для получения всех вопросов.
     *
     * @return Все вопросы
     */
    List<QuestionData> getAll();

    /**
     * Метод для получения одного вопроса.
     *
     * @param questionId Идентификатор вопроса
     * @return Один вопрос
     */
    QuestionData getOne(Long questionId);

    /**
     * Обновить вопрос.
     *
     * @param questionId Идентификатор вопроса
     * @param request Запрос
     */
    void update(Long questionId, QuestionUpdateRequest request);

}