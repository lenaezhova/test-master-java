package com.testmaster.controller;

import api.api.MailApi;
import api.domain.mail.request.SendConfirmEmailRequest;
import com.testmaster.service.AuthService.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(MailApi.PATH)
public class MailController implements MailApi {
    private final MailService mailService;

    @Override
    public ResponseEntity<Object> sendConfirmEmail(SendConfirmEmailRequest request) {
        mailService.sendConfirmEmail(request.email(), request.link());

        return ResponseEntity
                .ok()
                .build();
    }
}
