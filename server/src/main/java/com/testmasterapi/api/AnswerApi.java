package com.testmasterapi.api;

import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answer.request.AnswerCreateRequest;
import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Вопросы", description = "API для работы c вопросами")
public interface AnswerApi {
    String PATH = "/api/answers";

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/questions/{questionId}")
    @Operation(summary = "Получение всех ответов на вопрос")
    List<AnswerData> allByQuestionId(@PathVariable("questionId") Long questionId);

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/tests-sessions/{testSessionId}/questions/{questionId}/answers-templates/{answerTemplateId}")
    @Operation(
            summary = "Создать ответ",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ответ создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании ответа"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    ResponseEntity<Void> create(
            @PathVariable Long testSessionId,
            @PathVariable Long questionId,
            @PathVariable Long answerTemplateId,
            @RequestBody AnswerCreateRequest request
    );

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить ответ",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация в ответе обновлена"),
                    @ApiResponse(responseCode = "404", description = "Шаблон ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void update(@PathVariable Long id, @RequestBody AnswerUpdateRequest request);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить ответ",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ответ удален"),
                    @ApiResponse(responseCode = "404", description = "Ответ с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void delete(@PathVariable Long id);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/questions/{questionId}")
    @Operation(
            summary = "Удалить все ответы у вопроса",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ответы удалены"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void deleteAllByQuestionId(@PathVariable Long questionId);
}
