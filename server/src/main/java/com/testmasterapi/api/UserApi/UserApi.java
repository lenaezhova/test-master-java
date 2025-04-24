package com.testmasterapi.api.UserApi;

import com.testmasterapi.api.TestSessionApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.response.TestsSessionsResponse;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Пользователи")
public interface UserApi {
    String BASE_PATH = "/users";
    String PATH = "/api" + BASE_PATH;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Получение списка пользователей")
    PageData<UserData> all(Boolean showDeleted, Pageable pageable);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}" + TestSessionApi.BASE_PATH)
    @Operation(summary = "Получение всех сессий тестов пользователя")
    PageData<TestSessionData> allSessions(@PathVariable("id") Long id, Boolean showDeleted, Pageable pageable);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить пользователя",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден", content = @Content())
            }
    )
    UserData one(@PathVariable Long id);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current")
    @Operation(
            summary = "Получить текущего авторизованного пользователя",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден", content = @Content())
            }
    )
    UserData current();

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить информацию о пользователе",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о пользователе обновлена"),
                    @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
            }
    )
    void update(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить пользователя",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь удален"),
                    @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
            }
    )
    void delete(@PathVariable Long id);
}
