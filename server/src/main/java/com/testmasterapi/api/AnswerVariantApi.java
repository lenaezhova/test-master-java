package com.testmasterapi.api;

import com.testmasterapi.domain.answerVariant.data.AnswerVariantPrivateData;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantCreateRequest;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Вопросы", description = "API для работы c вопросами")
public interface AnswerVariantApi {
    String PATH = "/api/answer-variants";

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/questions/{questionId}")
    @Operation(summary = "Получение списка всех вариантов ответа у теста")
    List<AnswerVariantPrivateData> allAnswerVariantsQuestion(@PathVariable Long questionId);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Получение списка всех вариантов ответа")
    List<AnswerVariantPrivateData> all();

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping
    @Operation(
            summary = "Создать вариант ответа",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Вариант ответа создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании варианта ответа")
            }
    )
    ResponseEntity<Void> create(@RequestBody AnswerVariantCreateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить информацию о вариаенте ответа",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о варианте ответа обновлена"),
                    @ApiResponse(responseCode = "404", description = "Вариант ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Варинато ответа может редактировать только создатель теста")
            }
    )
    void update(@PathVariable Long id, @RequestBody AnswerVariantUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить вариант ответа",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Вариант ответа удален"),
                    @ApiResponse(responseCode = "404", description = "Вариант ответа с таким идентификатором не найден")
            }
    )
    void delete(@PathVariable Long id);
}