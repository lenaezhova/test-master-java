package com.testmaster.service.QuestionService.QuestionAnswerTemplate;

import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с вопросами.
 *
 */
public interface QuestionAnswerTemplatesService {

    /**
     * Метод для получения всех шаблонов ответов вопроса.
     * @param questionId Идентификатор вопроса
     * @return Все шаблоны ответов вопроса
     */
    List<AnswerTemplateData> getAllAnswerTemplate(Long questionId);

    /**
     * Метод для создания нового шаблона ответа.
     * @param questionId Идентификатор вопроса
     * @param request Запрос
     * @return Созданный шаблон ответа
     */
    @NotNull
    AnswerTemplateData createAnswerTemplate(Long questionId, @NotNull AnswerTemplateCreateRequest request);

    /**
     * Метод для удаления всех шаблонов ответа у вопроса.
     *
     * @param questionId Идентификатор вопроса
     */
    void deleteAllAnswerTemplate(Long questionId);
}