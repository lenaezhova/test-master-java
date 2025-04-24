package com.testmaster.service.QuestionService.QuestionAnswerService;

import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.page.data.PageData;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Интерфейс для работы с вопросами.
 *
 */
public interface QuestionAnswerService {

    /**
     * Метод для получения всех ответов на вопрос.
     * @param questionId Идентификатор вопроса
     * @return Все ответы на вопрос
     */
    @NotNull
    PageData<AnswerData> getAllAnswers(Long questionId, Boolean showDeletedQuestion, @NotNull Pageable pageable);

    /**
     * Метод для удаления всех ответов у вопроса
     *
     * @param questionId Идентификатор вопроса
     */
    void deleteAllAnswers(Long questionId);
}