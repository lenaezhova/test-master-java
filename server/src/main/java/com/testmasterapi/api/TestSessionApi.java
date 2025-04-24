package com.testmasterapi.api;

import com.testmasterapi.api.QuestionApi.QuestionApi;
import com.testmasterapi.api.TestApi.TestApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.request.TestSessionAddAnswerRequest;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionAddTestAnswerRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import com.testmasterapi.domain.testSession.response.TestsSessionsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Сессии тестов")
public interface TestSessionApi {
    String BASE_PATH = TestApi.BASE_PATH + "-sessions";
    String PATH = "/api" + BASE_PATH;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Получение списка всех сессий")
    PageData<TestSessionData> all(
            @Parameter(description = "Показать сессии только удаленных тестов", example = "false")
            @RequestParam(required = false)
            Boolean showOnlyTestDeleted,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{testSessionId}")
    @Operation(summary = "Получение сессии")
    TestSessionData one(@PathVariable Long testSessionId);

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{testSessionId}")
    @Operation(
            summary = "Обновить информацию о сессии",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о сессии обновлена"),
                    @ApiResponse(responseCode = "404", description = "Сессия с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void update(@PathVariable Long testSessionId, @RequestBody TestSessionUpdateRequest request);

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{testSessionId}:close")
    @Operation(
            summary = "Закрыть сессию",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Сессия закрыта обновлена"),
                    @ApiResponse(responseCode = "404", description = "Сессия с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void close(@PathVariable Long testSessionId);

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{testSessionId}" + AnswerApi.BASE_PATH)
    @Operation(
            summary = "Сохранение ответов на весь тест",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ответы на тест сохранены"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при сохранении ответа"),
                    @ApiResponse(responseCode = "404", description = "Сессия теста с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "404", description = "Шаблона ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    ResponseEntity<Void> createTestAnswer(
            @PathVariable Long testSessionId,
            @RequestBody TestSessionAddTestAnswerRequest request
    );

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{testSessionId}" + AnswerApi.BASE_PATH)
    @Operation(
            summary = "Обновление результата всего теста",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ответы на тест сохранены"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при сохранении ответа"),
                    @ApiResponse(responseCode = "404", description = "Сессия теста с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "404", description = "Шаблона ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void updateTestAnswer(
            @PathVariable Long testSessionId,
            @RequestBody TestSessionAddTestAnswerRequest request
    );

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{testSessionId}" + QuestionApi.BASE_PATH + "/{questionId}" + AnswerApi.BASE_PATH)
    @Operation(
            summary = "Создание ответа на вопрос",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ответ сохранен"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании ответа"),
                    @ApiResponse(responseCode = "404", description = "Сессия теста с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "404", description = "Шаблона ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    ResponseEntity<Void> createQuestionAnswer(
            @PathVariable Long testSessionId,
            @PathVariable Long questionId,
            @RequestBody TestSessionAddAnswerRequest request
    );

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{testSessionId}" + QuestionApi.BASE_PATH + "/{questionId}" + AnswerApi.BASE_PATH)
    @Operation(
            summary = "Обновление ответа на вопрос",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ответ обновлен"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при обновлении ответа"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста (если тест закрыт)"),
                    @ApiResponse(responseCode = "404", description = "Сессия теста с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "404", description = "Шаблона ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void updateQuestionAnswer(
            @PathVariable Long testSessionId,
            @PathVariable Long questionId,
            @RequestBody TestSessionAddAnswerRequest request
    );

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{testSessionId}")
    @Operation(
            summary = "Удалить сессию",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Сессия удалена"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Сессия с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void delete(@PathVariable Long testSessionId);
}
