package com.testmasterapi.api.QuestionApi;

import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.reponse.QuestionsResponse;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    PageData<QuestionData> all(
            @Parameter( description = "Показать только удаленные вопросы (удаленные не через тест)", example = "false")
            @RequestParam(required = false)
            Boolean showOnlySoftDeleted,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @GetMapping("/{questionId}")
    @Operation(summary = "Получение вопроса")
    QuestionData one(@PathVariable Long questionId);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{questionId}")
    @Operation(
            summary = "Обновить информацию о вопросе",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о вопросе обновлена"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void update(@PathVariable Long questionId, @RequestBody QuestionUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{questionId}")
    @Operation(
            summary = "Удалить вопрос",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Вопрос удален"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста"),
                    @ApiResponse(responseCode = "404", description = "Вопрос с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения"),
            }
    )
    void delete(@PathVariable Long questionId);
}
