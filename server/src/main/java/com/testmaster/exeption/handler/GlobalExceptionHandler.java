package com.testmaster.exeption.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.testmaster.exeption.AuthException;
import com.testmaster.exeption.ClientException;
import kotlin.io.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = this.getBody(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error"
        );
        ex.printStackTrace();
        return ResponseEntity.status(500).body(body);
    }

    @ExceptionHandler({AuthException.class, AuthorizationDeniedException.class, SignatureVerificationException.class, JWTDecodeException.class})
    public ResponseEntity<Map<String, Object>> handleAuthException(Exception ex) {
        Map<String, Object> body = this.getBody(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(401).body(body);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Map<String, Object>> handleClientException(ClientException ex) {
        Map<String, Object> body = this.getBody(
                ex.getStatusCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    private Map<String, Object> getBody(Number code, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", code);
        body.put("message", message);
        body.put("timestamp", Instant.now().toString());
        return body;
    }
}