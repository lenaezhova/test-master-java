package com.testmaster.service.QuestionService;

import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Интерфейс для работы с вопросами.
 *
 */
public interface QuestionService {

    /**
     * Метод для получения всех вопросов.
     *
     * @param showOnlySoftDeleted Если <b>true</b> в результате будут только удаленные вопросы (удаленные не через тест)
     * @return Страница списка всех вопросов
     */
    @NotNull
    PageData<QuestionData> getAll(Boolean showOnlySoftDeleted, @NotNull Pageable pageable);

    /**
     * Метод для получения одного вопроса.
     *
     * @param questionId Идентификатор вопроса
     * @return Один вопрос
     */
    QuestionData getOne(Long questionId);

    /**
     * Обновить вопрос.
     *
     * @param questionId Идентификатор вопроса
     * @param request Запрос
     */
    void update(Long questionId, QuestionUpdateRequest request);

}