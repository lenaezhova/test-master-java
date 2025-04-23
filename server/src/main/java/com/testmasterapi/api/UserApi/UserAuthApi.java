package com.testmasterapi.api.UserApi;

import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.testmasterapi.domain.user.request.UserLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Авторизация")
public interface UserAuthApi {
    String PATH = UserApi.PATH + "/auth";

    @PostMapping("/registration")
    @Operation(summary = "Регистрация")
    ResponseEntity<Object> registration(@RequestBody UserCreateRequest request, HttpServletResponse response);

    @PostMapping("/login")
    @Operation(summary = "Войти в систему с помощью пароля")
    ResponseEntity<Object> login(@RequestBody UserLoginRequest request, HttpServletResponse response);

    @PostMapping("/logout")
    @Operation(summary = "Выйти из системы")
    ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response);

    @PostMapping("/refresh")
    @Operation(summary = "Обновить рефреш токен")
    ResponseEntity<Object> refresh(HttpServletRequest request,
                                 HttpServletResponse response);

    @GetMapping("/activate/{link}")
    @Operation(summary = "Активация аккаунта по ссылке")
    ResponseEntity<Object> activate(@PathVariable String link);
}
