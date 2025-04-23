package com.testmasterapi.api;

import com.testmasterapi.api.QuestionApi.QuestionApi;
import com.testmasterapi.api.TestApi.TestApi;
import com.testmasterapi.domain.answer.request.AnswerCreateRequest;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    List<TestSessionData> all();

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @Operation(summary = "Получение сессии")
    TestSessionData one(@PathVariable Long id);

    @PreAuthorize("isAuthenticated()")
    @PostMapping(
            "/{id}" +
            QuestionApi.BASE_PATH + "/{questionId}" +
            AnswerTemplateApi.BASE_PATH + "/{answerTemplateId}"  +
            AnswerApi.BASE_PATH
    )
    @Operation(
            summary = "Создать ответ",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ответ создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании ответа"),
                    @ApiResponse(responseCode = "404", description = "Сессия теста с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "404", description = "Шаблона ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    ResponseEntity<Void> createAnswer(
            @PathVariable Long id,
            @PathVariable Long questionId,
            @PathVariable Long answerTemplateId,
            @RequestBody AnswerCreateRequest request
    );

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
