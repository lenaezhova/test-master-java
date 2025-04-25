package com.testmasterapi.api.TestApi;

import com.testmaster.model.Answer;
import com.testmasterapi.api.AnswerTemplateApi;
import com.testmasterapi.api.QuestionApi.QuestionApi;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.reponse.QuestionsResponse;
import com.testmasterapi.domain.question.reponse.QuestionsWithTemplatesResponse;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionCreateWithAnswersTemplatesRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    QuestionsResponse allQuestions(
            @PathVariable Long testId,

            @Parameter(description = "Показывать удаленные вопросы (удаленные не через тест)", example = "false")
            @RequestParam(required = false)
            Boolean showQuestionSoftDeleted
    );

    @PreAuthorize("isAuthenticated()")
    @GetMapping(AnswerTemplateApi.BASE_PATH)
    @Operation(summary = "Получение всех вопросов с шаблонами теста")
    QuestionsWithTemplatesResponse allQuestionsAnswerTemplates(
            @PathVariable Long testId,

            @Parameter(description = "Показывать удаленные вопросы (удаленные не через тест)", example = "false")
            @RequestParam(required = false)
            Boolean showQuestionSoftDeleted
    );

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping
    @Operation(
            summary = "Создать вопрос",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Вопрос создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании вопроса", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения", content = @Content()),
            }
    )
    ResponseEntity<Void> createQuestion(@PathVariable Long testId, @RequestBody QuestionCreateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping(AnswerTemplateApi.BASE_PATH)
    @Operation(
            summary = "Создать вопрос с шаблонами ответов",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Вопрос создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании вопроса", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения", content = @Content()),
            }
    )
    ResponseEntity<Void> createQuestionWithTemplates(@PathVariable Long testId, @RequestBody QuestionCreateWithAnswersTemplatesRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping
    @Operation(
            summary = "Удалить все вопросы теста",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Вопросы удалены"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Тест с таким идентификатором не найден", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения", content = @Content()),
            }
    )
    void deleteAllQuestions(@PathVariable Long testId);
}
