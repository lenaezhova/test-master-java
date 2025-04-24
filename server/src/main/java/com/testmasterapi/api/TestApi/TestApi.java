package com.testmasterapi.api.TestApi;

import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.TestResultDetailLevel;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.testSession.data.TestSessionResultData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Тесты")
public interface TestApi {
    String BASE_PATH = "/tests";
    String PATH = "/api" + BASE_PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение списка всех тестов")
    PageData<TestData> all(
            @Parameter(description = "Показать только удаленные тесты", example = "false")
            @RequestParam(required = false)
            Boolean showOnlyDeleted,

            @Parameter(description = "Фильтрация по статуса теста")
            @RequestParam(required = false)
            TestStatus status,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{testId}/results")
    @Operation(
            summary = "Получить результаты теста",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse( responseCode = "404", description = "Тест с таким идентификатором не найден")
            }
    )
    PageData<TestSessionResultData> results(
            @PathVariable Long testId,

            @Parameter(description = "Фильтрация деталей в результатах тестирования")
            @RequestParam(required = false)
            TestResultDetailLevel detailLevel,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{testId}")
    @Operation(
            summary = "Получить тест",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse( responseCode = "404", description = "Тест с таким идентификатором не найден")
            }
    )
    TestData one(@PathVariable Long testId);

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
    @PatchMapping("/{testId}")
    @Operation(
            summary = "Обновить тест",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест обновлен"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void update(@PathVariable Long testId, @RequestBody TestUpdateRequest test);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{testId}:open")
    @Operation(
            summary = "Открыть тест для прохождения",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест открыт для прохождения"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден"),
            }
    )
    void open(@PathVariable Long testId);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{testId}:close")
    @Operation(
            summary = "Закрыть тест для прохождения",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Сессия теста открыта"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Сессия теста с таким идентификатором не найден"),
            }
    )
    void close(@PathVariable Long testId);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{testId}")
    @Operation(
            summary = "Удалить тест",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест удален"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void delete(@PathVariable Long testId);
}
