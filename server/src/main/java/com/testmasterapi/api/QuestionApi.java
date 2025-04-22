package com.testmasterapi.api;

import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Вопросы", description = "API для работы c вопросами")
public interface QuestionApi {
    String PATH = "/api/questions";

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Получение списка всех вопросов")
    List<QuestionData> all();

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/tests/{testId}")
    @Operation(summary = "Получение всех вопросов теста")
    List<QuestionData> allQuestionsByTestId(@PathVariable("testId") Long testId);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Получение вопроса")
    QuestionData one(@PathVariable Long id);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping("/tests/{testId}")
    @Operation(
            summary = "Создать вопрос",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Вопрос создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании вопроса"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    ResponseEntity<Void> create(@PathVariable Long testId, @RequestBody QuestionCreateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить информацию о вопросе",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о вопросе обновлена"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void update(@PathVariable Long id, @RequestBody QuestionUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить вопрос",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Вопрос удален"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void delete(@PathVariable Long id);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/tests/{testId}")
    @Operation(
            summary = "Удалить все вопросы теста",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Вопросы удалены"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void deleteAllQuestion(@PathVariable Long testId);
}
