package api.api;

import api.domain.user.request.CreateUserRequest;
import api.domain.user.request.LoginRequest;
import api.domain.user.request.RefreshTokenRequest;
import com.testmaster.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Пользователи", description = "API для работы c пользователями")
public interface UserApi {
    String PATH = "/api/users";

    @PostMapping("/auth/registration")
    @Operation(summary = "Регистрация")
    ResponseEntity<Object> registration(@RequestBody CreateUserRequest request);

    @PostMapping("/auth/login")
    @Operation(summary = "Войти в систему с помощью пароля")
    ResponseEntity<Object> login(@RequestBody LoginRequest request);

    @PostMapping("/auth/logout")
    @Operation(summary = "Выйти из системы")
    ResponseEntity<Object> logout(@RequestBody RefreshTokenRequest request);

    @PostMapping("/auth/refresh")
    @Operation(summary = "Обновить рефреш токен")
    ResponseEntity<Object> refresh(@RequestBody RefreshTokenRequest request,
                                 HttpServletResponse response);

    @GetMapping("/auth/activate/{link}")
    @Operation(summary = "Активация аккаунта по ссылке")
    ResponseEntity<Object> activate(@PathVariable String link);

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    ResponseEntity<UserDto> getUser(@PathVariable Long id);
}
