package com.testmasterapi.api.QuestionApi;

import com.testmasterapi.api.AnswerApi;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answer.response.AnswersResponse;
import com.testmasterapi.domain.page.data.PageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ответы на вопрос")
@RequestMapping(value = QuestionApi.PATH + "/{questionId}" + AnswerApi.BASE_PATH)
public interface QuestionAnswersApi {
    String PATH = QuestionApi.PATH + "/{questionId}" + AnswerApi.BASE_PATH;

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @GetMapping
    @Operation(summary = "Получение всех ответов на вопрос")
    PageData<AnswerData> allAnswers(
            @PathVariable("questionId") Long questionId,

            @Parameter( description = "Показать ответы только на удаленные вопросы", example = "false")
            @RequestParam(required = false)
            Boolean showOnlyDeletedQuestion,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    @Operation(
            summary = "Удалить все ответы у вопроса",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ответы удалены"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения", content = @Content()),
            }
    )
    void deleteAllAnswers(@PathVariable Long questionId);
}
