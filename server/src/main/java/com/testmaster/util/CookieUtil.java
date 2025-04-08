package com.testmaster.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    private static final String REFRESH_TOKEN_NAME = "refreshToken";
    private static final int REFRESH_TOKEN_EXPIRATION = 30 * 24 * 60 * 60; // 30 дней
    private static final String PATH = (System.getenv("CLIENT_COOKIE_PATH"));

    public static void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshCookie = new Cookie(REFRESH_TOKEN_NAME, refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setMaxAge(REFRESH_TOKEN_EXPIRATION);
        refreshCookie.setPath(PATH);
        response.addCookie(refreshCookie);
    }

    public static void deleteRefreshTokenCookie(HttpServletResponse response) {
        Cookie deleteCookie = new Cookie(REFRESH_TOKEN_NAME, "");
        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure(true);
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath(PATH);
        response.addCookie(deleteCookie);
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