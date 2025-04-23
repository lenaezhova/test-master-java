package com.testmasterapi.api.TestApi;

import com.testmasterapi.api.GroupApi.GroupApi;
import com.testmasterapi.api.QuestionApi.QuestionApi;
import com.testmasterapi.api.TestSessionApi;
import com.testmasterapi.api.UserApi.UserApi;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.test.request.TestUpdateStatusRequest;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Сессии теста")
@RequestMapping(value = TestApi.PATH + "/{testId}" + TestSessionApi.BASE_PATH)
public interface TestTestsSessionsApi {
    String PATH = TestApi.PATH + "/{testId}" + TestSessionApi.BASE_PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение всех сессий теста")
    List<TestSessionData> allSessions(@PathVariable("testId") Long testId);

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
    ResponseEntity<Void> createSession(@PathVariable("testId") Long testId, @RequestBody TestSessionCreateRequest request);
}
