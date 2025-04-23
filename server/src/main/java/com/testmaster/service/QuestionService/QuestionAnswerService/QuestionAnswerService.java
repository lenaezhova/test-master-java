package com.testmaster.service.QuestionService.QuestionAnswerService;

import com.testmasterapi.domain.answer.data.AnswerData;

import java.util.List;

/**
 * Интерфейс для работы с вопросами.
 *
 */
public interface QuestionAnswerService {

    /**
     * Метод для получения всех ответов на вопрос.
     * @param questionId Идентификатор вопроса
     * @return Все ответы на вопрос
     */
    List<AnswerData> getAllAnswers(Long questionId);

    /**
     * Метод для удаления всех ответов у вопроса
     *
     * @param questionId Идентификатор вопроса
     */
    void deleteAllAnswers(Long questionId);
}