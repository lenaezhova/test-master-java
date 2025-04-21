package com.testmasterapi.api;

import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.testmasterapi.domain.user.request.UserLoginRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Пользователи", description = "API для работы c пользователями")
public interface UserApi {
    String PATH = "/api/users";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Получение списка пользователей")
    List<UserData> all();

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
            summary = "Получить авторизованного пользователя",
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

    @PostMapping("/auth/registration")
    @Operation(summary = "Регистрация")
    ResponseEntity<Object> registration(@RequestBody UserCreateRequest request, HttpServletResponse response);

    @PostMapping("/auth/login")
    @Operation(summary = "Войти в систему с помощью пароля")
    ResponseEntity<Object> login(@RequestBody UserLoginRequest request, HttpServletResponse response);

    @PostMapping("/auth/logout")
    @Operation(summary = "Выйти из системы")
    ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response);

    @PostMapping("/auth/refresh")
    @Operation(summary = "Обновить рефреш токен")
    ResponseEntity<Object> refresh(HttpServletRequest request,
                                 HttpServletResponse response);

    @GetMapping("/auth/activate/{link}")
    @Operation(summary = "Активация аккаунта по ссылке")
    ResponseEntity<Object> activate(@PathVariable String link);

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
