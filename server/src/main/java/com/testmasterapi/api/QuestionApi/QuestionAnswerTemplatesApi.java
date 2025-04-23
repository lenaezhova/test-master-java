package com.testmasterapi.api.QuestionApi;

import com.testmasterapi.api.AnswerTemplateApi;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Шаблоны ответов вопроса")
@RequestMapping(value = QuestionApi.PATH + "/{questionId}" + AnswerTemplateApi.BASE_PATH)
public interface QuestionAnswerTemplatesApi {
    String PATH = QuestionApi.PATH + "/{questionId}" + AnswerTemplateApi.BASE_PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение всех шаблонов ответа по вопросу")
    List<AnswerTemplateData> allAnswerTemplate(@PathVariable("questionId") Long id);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping
    @Operation(
            summary = "Создать шаблон ответа",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Шаблон ответа создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании шаблона ответа"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    ResponseEntity<Void> createAnswerTemplate(@PathVariable Long questionId, @RequestBody AnswerTemplateCreateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping
    @Operation(
            summary = "Удалить все шаблоны ответа вопроса",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Вопросы удалены"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void deleteAllAnswerTemplate(@PathVariable Long questionId);
}
