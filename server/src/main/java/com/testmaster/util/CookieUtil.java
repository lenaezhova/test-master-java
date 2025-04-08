package com.testmaster.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    private static final String REFRESH_TOKEN_NAME = "refreshToken";
    private static final int REFRESH_TOKEN_EXPIRATION = 30 * 24 * 60 * 60; // 30 дней

    public static void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        String cookieHeader = String.format(
                "%s=%s; Max-Age=%d; Path=/; Secure; HttpOnly; SameSite=None",
                REFRESH_TOKEN_NAME,
                refreshToken,
                REFRESH_TOKEN_EXPIRATION
        );

        response.setHeader("Set-Cookie", cookieHeader);
    }

    public static void deleteRefreshTokenCookie(HttpServletResponse response) {
        String cookieHeader = String.format(
                "%s=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/; Secure; HttpOnly; SameSite=None",
                REFRESH_TOKEN_NAME
        );
        response.setHeader("Set-Cookie", cookieHeader);
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}