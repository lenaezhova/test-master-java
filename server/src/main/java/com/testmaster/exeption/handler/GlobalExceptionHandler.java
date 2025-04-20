package com.testmaster.exeption.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.testmaster.exeption.AuthException;
import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import kotlin.io.AccessDeniedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({NotFoundException.class, HttpRequestMethodNotSupportedException.class, NoResourceFoundException.class})
    public ResponseEntity<?> notFoundHandler(Exception ex) {
        LOG.error("Not found error", ex);

        var response = ResponseEntity.status(HttpStatus.NOT_FOUND);
        if (StringUtils.isNotBlank(ex.getMessage())) {
            return response.body(ex.getMessage());
        }
        return response.build();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> notFoundHandler(HttpMessageNotReadableException ex) {
        LOG.error("Bad request error", ex);

        var response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        if (StringUtils.isNotBlank(ex.getMessage())) {
            return response.body(ex.getMessage());
        }
        return response.build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        LOG.error("Internal server error", ex);

        Map<String, Object> body = this.getBody(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error"
        );
        ex.printStackTrace();
        return ResponseEntity.status(500).body(body);
    }

    @ExceptionHandler({AuthException.class, AuthorizationDeniedException.class, SignatureVerificationException.class, JWTDecodeException.class})
    public ResponseEntity<Map<String, Object>> handleAuthException(Exception ex) {
        LOG.error("UNAUTHORIZED", ex);

        Map<String, Object> body = this.getBody(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(401).body(body);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Map<String, Object>> handleClientException(ClientException ex) {
        LOG.error("Client exception", ex);

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