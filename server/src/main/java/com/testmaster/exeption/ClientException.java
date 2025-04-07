package com.testmaster.exeption;

import org.springframework.http.HttpStatus;

public class ClientException extends RuntimeException {
    private final int statusCode;

    public ClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ClientException(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static ClientException UnprocessableEntity(String message) {
        return new ClientException(message, HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}
