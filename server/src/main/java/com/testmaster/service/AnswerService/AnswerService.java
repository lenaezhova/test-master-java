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
}