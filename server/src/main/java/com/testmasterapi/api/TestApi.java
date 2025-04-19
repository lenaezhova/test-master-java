package com.testmasterapi.api;

import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Тесты", description = "API для работы c тестами")
public interface TestApi {
    String PATH = "/api/tests";

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение списка всех тестов")
    List<TestData> all();

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить тест",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Тест с таким идентификатором не найден",
                            content = @Content()
                    )
            }
    )
    TestData one(@PathVariable Long id);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping
    @Operation(
            summary = "Создать тест",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Тест создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании теста")
            }
    )
    ResponseEntity<Void> create(@RequestBody TestCreateRequest test);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить тест",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест обновлен"),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден")
            }
    )
    void update(@PathVariable Long id, @RequestBody TestUpdateRequest test);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить тест",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест удален"),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден")
            }
    )
    void delete(@PathVariable Long id);
}
