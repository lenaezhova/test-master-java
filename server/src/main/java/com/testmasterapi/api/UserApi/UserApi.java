package com.testmasterapi.api.UserApi;

import com.testmasterapi.api.TestSessionApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserUpdateCurrentRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
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

@Tag(name = "Пользователи")
public interface UserApi {
    String BASE_PATH = "/users";
    String PATH = "/api" + BASE_PATH;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Получение списка пользователей")
    PageData<UserData> all(
            @Parameter( description = "Показать только удалённых пользователей", example = "false")
            @RequestParam(required = false)
            Boolean showOnlyDeleted,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}" + TestSessionApi.BASE_PATH)
    @Operation(summary = "Получение всех сессий тестов пользователя")
    PageData<TestSessionData> allSessions(
            @PathVariable Long userId,

            @Parameter( description = "Показать сессии с удаленными тестами", example = "false")
            @RequestParam(required = false)
            Boolean showTestDeleted,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}")
    @Operation(
            summary = "Получить пользователя",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден", content = @Content())
            }
    )
    UserData one(@PathVariable Long userId);

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
    @PatchMapping
    @Operation(
            summary = "Обновить информацию текущего авторизованного пользователя",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о пользователе обновлена"),
                    @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден", content = @Content())
            }
    )
    void updateCurrent(@RequestBody UserUpdateCurrentRequest userUpdateRequest);

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}")
    @Operation(
            summary = "Обновить информацию о пользователе",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Роль пользователя обновлена"),
                    @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден", content = @Content())
            }
    )
    void update(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRoleRequest);
}
