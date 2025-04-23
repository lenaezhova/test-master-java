package com.testmasterapi.api.QuestionApi;

import com.testmasterapi.api.AnswerTemplateApi;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Вопросы")
public interface QuestionApi {
    String BASE_PATH = "/questions";
    String PATH = "/api" + BASE_PATH;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Получение списка всех вопросов")
    List<QuestionData> all();

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Получение вопроса")
    QuestionData one(@PathVariable Long id);

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
}
