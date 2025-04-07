package api.api;

import com.testmaster.dto.TestDto;
import com.testmaster.model.TestModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Тесты", description = "API для работы c тестами")
public interface TestApi {
    String PATH = "/api/test";

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получить все тесты")
    ResponseEntity<List<TestDto>> all();

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @Operation(summary = "Создать тест")
    ResponseEntity<Object> create(@RequestBody TestModel test);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @Operation(summary = "Получить тест")
    ResponseEntity<TestDto> one(@PathVariable Long id);

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    @Operation(summary = "Обновить тест")
    ResponseEntity<Object> update(@PathVariable Long id, @RequestBody TestModel test);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тест")
    ResponseEntity<Object> delete(@PathVariable Long id);
}
