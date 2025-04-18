package com.testmaster.util;

import com.testmasterapi.domain.user.JwtTokenPair;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    private static final String ACCESS_TOKEN_NAME = "accessToken";
    private static final int ACCESS_TOKEN_EXPIRATION = 30 * 24 * 60 * 30; // 15 дней

    private static final String REFRESH_TOKEN_NAME = "refreshToken";
    private static final int REFRESH_TOKEN_EXPIRATION = 30 * 24 * 60 * 60; // 30 дней

//    public static void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
//        String cookieHeader = String.format(
//                "%s=%s; Max-Age=%d; Path=/; Secure; HttpOnly; SameSite=None",
//                REFRESH_TOKEN_NAME,
//                refreshToken,
//                REFRESH_TOKEN_EXPIRATION
//        );
//
//        response.setHeader("Set-Cookie", cookieHeader);
//    }
//
//    public static void addAccessTokenCookie(HttpServletResponse response, String accessToken) {
//        String cookieHeader = String.format(
//                "%s=%s; Max-Age=%d; Path=/; Secure; HttpOnly; SameSite=None",
//                ACCESS_TOKEN_NAME,
//                accessToken,
//                ACCESS_TOKEN_EXPIRATION
//        );
//
//        response.setHeader("Set-Cookie", cookieHeader);
//    }

    public static void setTokensInCookie(HttpServletResponse response, JwtTokenPair jwtTokenPair) {
        String accessCookie = String.format(
                "%s=%s; Max-Age=%d; Path=/; Secure; HttpOnly; SameSite=None",
                ACCESS_TOKEN_NAME,
                jwtTokenPair.accessToken(),
                ACCESS_TOKEN_EXPIRATION
        );

        String refreshCookie = String.format(
                "%s=%s; Max-Age=%d; Path=/; Secure; HttpOnly; SameSite=None",
                REFRESH_TOKEN_NAME,
                jwtTokenPair.refreshToken(),
                REFRESH_TOKEN_EXPIRATION
        );

        response.addHeader("Set-Cookie", accessCookie);
        response.addHeader("Set-Cookie", refreshCookie);
    }

//    public static void deleteRefreshTokenCookie(HttpServletResponse response) {
//        String cookieHeader = String.format(
//                "%s=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/; Secure; HttpOnly; SameSite=None",
//                REFRESH_TOKEN_NAME
//        );
//        response.setHeader("Set-Cookie", cookieHeader);
//    }
//
//    public static void deleteAccessTokenCookie(HttpServletResponse response) {
//        String cookieHeader = String.format(
//                "%s=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/; Secure; HttpOnly; SameSite=None",
//                ACCESS_TOKEN_NAME
//        );
//        response.setHeader("Set-Cookie", cookieHeader);
//    }

    public static void deleteTokensFromCookie(HttpServletResponse response) {
        String refreshCookie = String.format(
                "%s=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/; Secure; HttpOnly; SameSite=None",
                REFRESH_TOKEN_NAME
        );
        String accessCookie = String.format(
                "%s=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/; Secure; HttpOnly; SameSite=None",
                ACCESS_TOKEN_NAME
        );
        response.setHeader("Set-Cookie", accessCookie);
        response.setHeader("Set-Cookie", refreshCookie);
    }

    public static String getAccessToken(HttpServletRequest request) {
        return CookieUtil.getCookieValue(request, ACCESS_TOKEN_NAME);
    }

    public static String getRefreshToken(HttpServletRequest request) {
        return CookieUtil.getCookieValue(request, REFRESH_TOKEN_NAME);
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