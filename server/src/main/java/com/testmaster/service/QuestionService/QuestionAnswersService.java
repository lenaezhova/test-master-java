package com.testmaster.service.QuestionService;

import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с вопросами.
 *
 */
public interface QuestionAnswersService {

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