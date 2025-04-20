package com.testmaster.service.QuestionTypeService;

import com.testmasterapi.domain.question.data.QuestionTypeData;
import com.testmasterapi.domain.question.request.QuestionTypeCreateRequest;
import com.testmasterapi.domain.question.request.QuestionTypeUpdateRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с типами вопросов.
 *
 */
public interface QuestionTypeService {
    /**
     * Метод для получения всех типов вопросов.
     *
     * @return Все типы вопросов
     */
    List<QuestionTypeData> getAll();

    /**
     * Метод для получения типа вопроса.
     * @param typeId Идентификатор типа вопроса
     * @return Тип вопроса
     */
    QuestionTypeData getOne(Long typeId);

    /**
     * Метод для создания типа вопроса
     *
     * @param request запрос
     */
    @NotNull
    QuestionTypeData create(@NotNull QuestionTypeCreateRequest request);

    /**
     * Метод для обновления типа вопроса
     * @param typeId Идентификатор типа вопроса
     * @param request запрос
     */
    void update(Long typeId, QuestionTypeUpdateRequest request);

    /**
     * Метод для удаления типа вопроса.
     *
     * @param typeId Идентификатор типа вопроса
     */
    void delete(Long typeId);
}
