package com.testmasterapi.api.TestApi;

import com.testmasterapi.api.QuestionApi.QuestionApi;
import com.testmasterapi.api.TestSessionApi;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.test.request.TestUpdateStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Вопросы теста")
@RequestMapping(value = TestApi.PATH + "/{testId}" + QuestionApi.BASE_PATH)
public interface TestQuestionApi {
    String PATH = TestApi.PATH + "/{testId}" + QuestionApi.BASE_PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение всех вопросов теста")
    List<QuestionData> allQuestions(@PathVariable Long testId);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping
    @Operation(
            summary = "Создать вопрос",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Вопрос создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании вопроса"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    ResponseEntity<Void> createQuestion(@PathVariable Long testId, @RequestBody QuestionCreateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping
    @Operation(
            summary = "Удалить все вопросы теста",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Вопросы удалены"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void deleteAllQuestions(@PathVariable Long testId);
}
