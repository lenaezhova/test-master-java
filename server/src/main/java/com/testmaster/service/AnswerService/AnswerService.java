package com.testmaster.service.AnswerService;

import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answer.request.AnswerCreateRequest;
import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с шаблонами ответов.
 *
 */
public interface AnswerService {

    /**
     * Метод для получения всех ответов на вопрос.
     * @param questionId Идентификатор вопроса
     * @return Все ответы на вопрос
     */
    List<AnswerData> getAllByQuestionId(Long questionId);

    /**
     * Метод для создания нового ответа на вопрос.
     * @param testSessionId Идентификатор сессии теста
     * @param questionId Идентификатор вопроса
     * @param answerTemplateId Идентификатор шаблона ответа
     * @param request Запрос
     * @return Созданный ответ
     */
    @NotNull
    AnswerData create(Long testSessionId, Long questionId, Long answerTemplateId, @NotNull AnswerCreateRequest request);

    /**
     * Обновить ответ.
     *
     * @param request Запрос
     */
    void update(Long answerId, AnswerUpdateRequest request);

    /**
     * Метод для удаления ответа.
     *
     * @param answerId Идентификатор ответа
     */
    void delete(Long answerId);

    /**
     * Метод для удаления всех ответов у вопроса
     *
     * @param questionId Идентификатор вопроса
     */
    void deleteAllByQuestionId(Long questionId);
}