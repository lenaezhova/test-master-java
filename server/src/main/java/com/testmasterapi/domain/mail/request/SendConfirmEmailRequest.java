package com.testmasterapi.domain.mail.request;

public record SendConfirmEmailRequest (
        String email,
        String link
) {
}
