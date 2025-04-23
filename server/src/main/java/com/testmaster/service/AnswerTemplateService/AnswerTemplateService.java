package com.testmaster.service.AnswerTemplateService;

import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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