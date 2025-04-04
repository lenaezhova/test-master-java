package com.testmaster.mapper;

import api.domain.user.JwtClaimNames;
import api.domain.user.UserRoles;
import api.domain.user.request.CreateUserRequest;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.model.UserModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public final class UserMapper {

    public static UserModel map(CreateUserRequest request) {
        LocalDateTime now = LocalDateTime.now();
        UserModel user = new UserModel();

        user.setEmail(request.email());
        user.setName(request.name());
        user.setPassword(request.password());
        user.setActivationLink("/test");
        user.setIsActivate(false);
        user.setRoles(List.of(UserRoles.USER));
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return user;
    }

    public static UserModel map(DecodedJWT jwt) {
        LocalDateTime now = LocalDateTime.now();
        UserModel user = new UserModel();

        user.setEmail(jwt.getClaim(JwtClaimNames.EMAIL).asString());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return user;
    }
}