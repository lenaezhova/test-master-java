package com.testmasterapi.api.QuestionApi;

import com.testmasterapi.api.AnswerTemplateApi;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Шаблоны ответов вопросов")
@RequestMapping(value = QuestionApi.PATH + "/{questionId}" + AnswerTemplateApi.BASE_PATH)
public interface QuestionAnswerTemplatesApi {
    String PATH = QuestionApi.PATH + "/{questionId}" + AnswerTemplateApi.BASE_PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение вопроса с шаблонами")
    QuestionWithTemplatesData oneWithTemplates(@PathVariable Long questionId);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{answerTemplateId}")
    @Operation(
            summary = "Обновить информацию в шаблоне ответа",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация в шаблоне ответа обновлена"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Шаблон ответа с таким идентификатором не найден", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения", content = @Content()),
            }
    )
    void updateAnswerTemplate(
            @PathVariable Long questionId,
            @PathVariable Long answerTemplateId,
            @RequestBody AnswerTemplateUpdateRequest request
    );

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{answerTemplateId}")
    @Operation(
            summary = "Удалить шаблон ответа",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Шаблон овтета удален"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Шаблон ответа с таким идентификатором не найден", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения", content = @Content()),
            }
    )
    void deleteAnswerTemplate(
            @PathVariable Long questionId,
            @PathVariable Long answerTemplateId
    );

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping
    @Operation(
            summary = "Обновить вопрос с шаблонами ответов",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Вопрос и шаблоны обновлены"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "В текстовом вопросе не может быть больше 1 варианта ответа", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения", content = @Content()),
            }
    )
    void updateWithTemplates(@PathVariable Long questionId, @RequestBody QuestionUpdateWithAnswersTemplatesRequest request);
}
