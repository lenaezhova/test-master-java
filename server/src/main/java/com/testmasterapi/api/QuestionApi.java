package com.testmasterapi.api;

import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Вопросы", description = "API для работы c вопросами")
public interface QuestionApi {
    String PATH = "/api/questions";

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @GetMapping
    @Operation(summary = "Получение списка всех вопросов")
    List<QuestionData> all();

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Получение вопроса")
    QuestionData one(@PathVariable Long id);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping
    @Operation(
            summary = "Создать вопрос",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Вопрос создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании вопроса")
            }
    )
    ResponseEntity<Void> create(@RequestBody QuestionCreateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить информацию о вопросе",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о вопросе обновлена"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден")
            }
    )
    void update(@PathVariable Long id, @RequestBody QuestionUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить вопрос",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Вопрос удален"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден")
            }
    )
    void delete(@PathVariable Long id);
}
