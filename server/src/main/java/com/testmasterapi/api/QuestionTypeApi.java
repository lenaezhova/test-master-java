package com.testmasterapi.api;

import com.testmasterapi.domain.question.data.QuestionTypeData;
import com.testmasterapi.domain.question.request.QuestionTypeCreateRequest;
import com.testmasterapi.domain.question.request.QuestionTypeUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Типы вопросов", description = "API для работы c типами вопросов")
public interface QuestionTypeApi {
    String PATH = QuestionApi.PATH + "/types";

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение списка всех типов вопросов")
    List<QuestionTypeData> all();

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{typeId}")
    @Operation(summary = "Получение типа вопроса")
    QuestionTypeData one(@PathVariable Long typeId);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping
    @Operation(
            summary = "Создать тип волроса",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Тип вопроса создана"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании типа вопроса")
            }
    )
    ResponseEntity<Void> create(@RequestBody QuestionTypeCreateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{typeId}")
    @Operation(
            summary = "Обновить информацию о типе вопроса",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о типе вопроса обновлена"),
                    @ApiResponse(responseCode = "404", description = "Тип вопроса с таким идентификатором не найден")
            }
    )
    void update(@PathVariable Long typeId, @RequestBody QuestionTypeUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{typeId}")
    @Operation(
            summary = "Удалить тип вопроса",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тип вопроса удален"),
                    @ApiResponse(responseCode = "404", description = "Тип вопроса с таким идентификатором не найден")
            }
    )
    void delete(@PathVariable Long typeId);
}
