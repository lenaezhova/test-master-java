package com.testmaster.controller;

import com.testmasterapi.api.MailApi;
import com.testmasterapi.domain.mail.request.SendConfirmEmailRequest;
import com.testmaster.service.MailService;
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
