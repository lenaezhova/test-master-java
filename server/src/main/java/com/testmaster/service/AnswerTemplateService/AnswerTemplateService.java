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
     * Метод для получения всех шаблонов ответов вопроса.
     * @param questionId Идентификатор вопроса
     * @return Все шаблоны ответов вопроса
     */
    List<AnswerTemplateData> getAllByQuestionId(Long questionId);

    /**
     * Метод для создания нового шаблона ответа.
     * @param questionId Идентификатор вопроса
     * @param request Запрос
     * @return Созданный шаблон ответа
     */
    @NotNull
    AnswerTemplateData create(Long questionId, @NotNull AnswerTemplateCreateRequest request);

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

    /**
     * Метод для удаления всех шаблонов ответа у вопроса.
     *
     * @param questionId Идентификатор вопроса
     */
    void deleteAllByQuestionId(Long questionId);
}