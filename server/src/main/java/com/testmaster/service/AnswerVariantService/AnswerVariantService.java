package com.testmaster.service.AnswerVariantService;

import com.testmasterapi.domain.answerVariant.data.AnswerVariantPrivateData;
import com.testmasterapi.domain.answerVariant.data.AnswerVariantPublicData;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantCreateRequest;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantUpdateRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с вариантами ответа.
 *
 */
public interface AnswerVariantService {

    /**
     * Метод для получения всех вариантов ответа вопроса.
     *
     * @param questionId Идентификатор вопроса
     * @return Все варианты ответа
     */
    List<AnswerVariantPrivateData> getAllQuestionAnswerVariants(Long questionId);

    /**
     * Метод для получения всех вариантов ответа.
     *
     * @return Все варианты ответа
     */
    List<AnswerVariantPrivateData> getAll();

    /**
     * Метод для получения варианта ответа.
     *
     * @param answerVariantId Идентификатор варианта ответа
     * @return Один варианта ответа
     */
    AnswerVariantPrivateData getOne(Long answerVariantId);

    /**
     * Метод для создания варианта ответа.
     *
     * @param request Запрос
     * @return Созданный вопрос
     */
    @NotNull
    AnswerVariantPublicData create(@NotNull AnswerVariantCreateRequest request);

    /**
     * Метод для обноввления вариант ответа.
     *
     * @param answerVariantId Идентификатор варианта ответа
     * @param request Запрос
     */
    void update(Long answerVariantId, AnswerVariantUpdateRequest request);

    /**
     * Метод для удаления варианта ответа.
     *
     * @param questionId Идентификатор вопроса
     */
    void delete(Long questionId);
}
