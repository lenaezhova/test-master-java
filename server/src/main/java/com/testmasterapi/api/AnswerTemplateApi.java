package com.testmasterapi.api;

import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
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

@Tag(name = "Шаблоны ответов", description = "API для работы c шаблонами ответов")
public interface AnswerTemplateApi {
    String PATH = "/api/answers-templates";

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/questions/{questionId}")
    @Operation(summary = "Получение всех шаблонов ответа по вопросу")
    List<AnswerTemplateData> allByQuestionId(@PathVariable("questionId") Long questionId);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping("/questions/{questionId}")
    @Operation(
            summary = "Создать шаблон ответа",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Шаблон ответа создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании шаблона ответа"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    ResponseEntity<Void> create(@PathVariable Long questionId, @RequestBody AnswerTemplateCreateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить информацию в шаблоне ответа",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация в шаблоне ответа обновлена"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Шаблон ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void update(@PathVariable Long id, @RequestBody AnswerTemplateUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить шаблон ответа",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Шаблон овтета удален"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Шаблон ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void delete(@PathVariable Long id);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/questions/{questionId}")
    @Operation(
            summary = "Удалить все шаблоны ответа вопроса",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Вопросы удалены"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void deleteAllByQuestionId(@PathVariable Long questionId);
}
