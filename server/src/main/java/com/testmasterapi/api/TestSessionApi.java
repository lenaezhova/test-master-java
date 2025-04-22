package com.testmasterapi.api;

import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Сессии тестов", description = "API для работы c сессиями тестов")
public interface TestSessionApi {
    String PATH = "/api/tests-sessions";

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Получение списка всех сессий")
    List<TestSessionData> all();

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users/{userId}")
    @Operation(summary = "Получение всех сессий пользователя")
    List<TestSessionData> allByUserId(@PathVariable("userId") Long userId);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @Operation(summary = "Получение сессии")
    TestSessionData one(@PathVariable Long id);

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/tests/{testId}")
    @Operation(
            summary = "Создать сессию",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Сессия создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании сессии теста"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    ResponseEntity<Void> create(@PathVariable Long testId, @RequestBody TestSessionCreateRequest request);

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить информацию о сессии",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о сессии обновлена"),
                    @ApiResponse(responseCode = "404", description = "Сессия с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void update(@PathVariable Long id, @RequestBody TestSessionUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить сессию",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Сессия удалена"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Сессия с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void delete(@PathVariable Long id);
}
