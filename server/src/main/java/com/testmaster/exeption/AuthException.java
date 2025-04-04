package com.testmaster.exeption;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AuthException jwtExpired(TokenExpiredException e) {
        return new AuthException("Authorization token has expired on " + e.getExpiredOn(), e);
    }

    public static AuthException jwtNotValid(JWTVerificationException e) {
        return new AuthException("Authorization token is not valid!", e);
    }
}
