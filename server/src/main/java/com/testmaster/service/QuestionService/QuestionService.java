package com.testmaster.service.QuestionService;

import com.testmasterapi.domain.group.data.GroupData;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import org.jetbrains.annotations.NotNull;

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
     * Метод для создания нового вопроса.
     *
     * @param request Запрос
     * @return Созданный вопрос
     */
    @NotNull
    QuestionData create(@NotNull QuestionCreateRequest request);

    /**
     * Обновить вопрос.
     *
     * @param questionId Идентификатор вопроса
     * @param request Запрос
     */
    void update(Long questionId, QuestionUpdateRequest request);

    /**
     * Метод для удаления вопроса.
     *
     * @param questionId Идентификатор вопроса
     */
    void delete(Long questionId);
}