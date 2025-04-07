package com.testmasterapi.api;

import com.testmasterapi.domain.mail.request.SendConfirmEmailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Почта", description = "API для работы c почтой")
public interface MailApi {
    String PATH = "/api/mail";

    @PostMapping
    @Operation(summary = "Отправить почту")
    ResponseEntity<Object> sendConfirmEmail(@RequestBody SendConfirmEmailRequest request);
}
