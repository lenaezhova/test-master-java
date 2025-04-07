package com.testmaster.mapper;

import api.domain.user.JwtClaimNames;
import api.domain.user.UserRoles;
import api.domain.user.request.CreateUserRequest;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.dto.UserDto;
import com.testmaster.model.UserModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public final class UserMapper {
    public UserDto toDto(UserModel user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getIsActivate(),
                user.getActivationLink(),
                user.getRoles(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static UserModel map(CreateUserRequest request, String activationLink) {
        LocalDateTime now = LocalDateTime.now();
        UserModel user = new UserModel();

        user.setEmail(request.email());
        user.setName(request.name());
        user.setPassword(request.password());
        user.setActivationLink(activationLink);
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