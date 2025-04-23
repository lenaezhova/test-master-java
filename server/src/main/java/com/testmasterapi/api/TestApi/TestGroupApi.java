package com.testmasterapi.api.TestApi;

import com.testmasterapi.api.GroupApi.GroupApi;
import com.testmasterapi.domain.group.request.TestsGroupAddRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Тесты группы")
@RequestMapping(value = TestApi.PATH + "/{testId}" + GroupApi.BASE_PATH)
public interface TestGroupApi {
    String PATH = TestApi.PATH + "/{testId}" + GroupApi.BASE_PATH;

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping("/{groupId}")
    @Operation(
            summary = "Добавить тест в группу",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест добавлен в группу"),
                    @ApiResponse(responseCode = "404", description = "Группа (тест) с таким идентификатором не найдены"),
                    @ApiResponse(responseCode = "409", description = "В группе уже есть тест с таким идентификатором")
            }
    )
    ResponseEntity<Void> addTest(
            @PathVariable("testId") Long testId,
            @PathVariable("groupId") Long groupId,
            @RequestBody TestsGroupAddRequest request
    );

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{groupId}")
    @Operation(
            summary = "Удалить тест из группы",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест удален из группы"),
                    @ApiResponse(responseCode = "404", description = "Группа (тест) с таким идентификатором не найдены")
            }
    )
    void deleteTest(
            @PathVariable("testId") Long testId,
            @PathVariable("groupId") Long groupId
    );
}
