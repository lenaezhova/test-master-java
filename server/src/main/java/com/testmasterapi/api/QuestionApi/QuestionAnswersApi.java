package com.testmasterapi.api.QuestionApi;

import com.testmasterapi.api.AnswerApi;
import com.testmasterapi.api.AnswerTemplateApi;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ответы на вопрос")
@RequestMapping(value = QuestionApi.PATH + "/{questionId}" + AnswerApi.BASE_PATH)
public interface QuestionAnswersApi {
    String PATH = QuestionApi.PATH + "/{questionId}" + AnswerApi.BASE_PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение всех ответов на вопрос")
    List<AnswerData> allAnswers(@PathVariable("questionId") Long questionId);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    @Operation(
            summary = "Удалить все ответы у вопроса",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ответы удалены"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void deleteAllAnswers(@PathVariable Long questionId);
}
