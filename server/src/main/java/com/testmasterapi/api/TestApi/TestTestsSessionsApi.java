package com.testmasterapi.api.TestApi;

import com.testmasterapi.api.TestSessionApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import com.testmasterapi.domain.testSession.response.TestsSessionsResponse;
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

import java.util.List;

@Tag(name = "Сессии теста")
@RequestMapping(value = TestApi.PATH + "/{testId}" + TestSessionApi.BASE_PATH)
public interface TestTestsSessionsApi {
    String PATH = TestApi.PATH + "/{testId}" + TestSessionApi.BASE_PATH;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Получение всех сессий теста")
    PageData<TestSessionData> allSessions(
            @PathVariable("testId") Long testId,

            @Parameter(description = "Показать сессии удаленных пользователей", example = "false")
            @RequestParam(required = false)
            Boolean showUserDeleted,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @Operation(
            summary = "Создать сессию",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Сессия создана"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании сессии теста"),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    ResponseEntity<Void> createSession(
            @PathVariable("testId") Long testId,
            @RequestBody TestSessionCreateRequest request
    );
}
