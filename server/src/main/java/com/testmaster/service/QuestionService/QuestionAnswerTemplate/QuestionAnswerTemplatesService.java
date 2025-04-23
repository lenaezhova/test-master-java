package com.testmaster.service.QuestionService.QuestionAnswerTemplate;

import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с вопросами.
 *
 */
public interface QuestionAnswerTemplatesService {

    /**
     * Метод для получения всех вопросов с шаблонами ответов.
     *
     * @return Все вопросы с шаблонами ответов
     */
    QuestionWithTemplatesData getOneWithTemplates(Long questionId);

    /**
     * Метод для обновления вопроса с шаблонами ответов.
     * Для обновления вопроса с шаблонами нужно обнвоить информацию о самом вопросе и так же информацию в шаблонах ответов.
     * Елси у шаблона не будет id, то нужно создать новый, при этом старый удалять не будем, чтобы остались ответы пользователей
     *
     * @param questionId Идентификатор вопроса
     * @param request Запрос
     */
    void updateWithTemplates(Long questionId, QuestionUpdateWithAnswersTemplatesRequest request);
}