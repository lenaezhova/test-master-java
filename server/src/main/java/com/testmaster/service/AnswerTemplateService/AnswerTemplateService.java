package com.testmaster.service.AnswerTemplateService;

import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;

/**
 * Интерфейс для работы с шаблонами ответов.
 *
 */
public interface AnswerTemplateService {

    /**
     * Обновить шаблон ответа.
     *
     * @param request Запрос
     */
    void update(Long answerTemplateId, AnswerTemplateUpdateRequest request);

    /**
     * Метод для удаления шаблона ответа.
     *
     * @param answerTemplateId Идентификатор шаблона ответа
     */
    void delete(Long answerTemplateId);
}