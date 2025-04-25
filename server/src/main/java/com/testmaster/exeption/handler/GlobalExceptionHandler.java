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

        return returnResponse(response, ex);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> notFoundHandler(HttpMessageNotReadableException ex) {
        LOG.error("Bad request error", ex);

        var response = ResponseEntity.status(HttpStatus.BAD_REQUEST);

        return returnResponse(response, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        LOG.error("Internal server error", ex);

        ex.printStackTrace();
        var response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

        return returnResponse(response, ex);
    }

    @ExceptionHandler({AuthException.class, AuthorizationDeniedException.class, SignatureVerificationException.class, JWTDecodeException.class})
    public ResponseEntity<?> handleAuthException(Exception ex) {
        LOG.error("UNAUTHORIZED", ex);

        var response = ResponseEntity.status(HttpStatus.UNAUTHORIZED);

        return returnResponse(response, ex);
    }

    @ExceptionHandler({IllegalArgumentException.class, AccessDeniedException.class})
    public ResponseEntity<?> handleIllegalArgumentException(Exception ex) {
        LOG.error("FORBIDDEN", ex);

        var response = ResponseEntity.status(HttpStatus.FORBIDDEN);

        return returnResponse(response, ex);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<?> handleClientException(ClientException ex) {
        LOG.error("Client exception", ex);

        var response = ResponseEntity.status(ex.getStatusCode());

        return returnResponse(response, ex);
    }

    private ResponseEntity<?> returnResponse(ResponseEntity.BodyBuilder response, Exception ex) {
        if (StringUtils.isNotBlank(ex.getMessage())) {
            return response.body(ex.getMessage());
        }

        return response.build();
    }
}