package com.testmaster.config.interceptor;

import com.testmaster.model.User.User;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final Map<Long, RequestInfo> requestCounts = new ConcurrentHashMap<>();
    private static final int LIMIT = 5;
    private static final long INTERVAL_MS = 1000;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        CustomUserDetails userDetails = getCurrentUserDetails();
        if (userDetails == null) return true;

        long userId = userDetails.getId();

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getBlockedAt() != null && user.getBlockedAt().isAfter(LocalDateTime.now())) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("User blocked until " + user.getBlockedAt());
                return false;
            }
        }

        long now = System.currentTimeMillis();
        requestCounts.putIfAbsent(userId, new RequestInfo(0, now));
        RequestInfo info = requestCounts.get(userId);

        synchronized (info) {
            if (now - info.timestamp > INTERVAL_MS) {
                info.timestamp = now;
                info.count = 1;
            } else {
                info.count++;
            }

            if (info.count > LIMIT) {
                userOpt.ifPresent(user -> {
                    user.setBlockedAt(LocalDateTime.now().plusMinutes(10));
                    userRepository.save(user);
                });

                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Too many requests. You are blocked for 10 minutes.");
                return false;
            }
        }

        return true;
    }

    private CustomUserDetails getCurrentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) auth.getPrincipal();
        }
        return null;
    }

    private static class RequestInfo {
        int count;
        long timestamp;

        public RequestInfo(int count, long timestamp) {
            this.count = count;
            this.timestamp = timestamp;
        }
    }
}