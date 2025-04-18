package com.testmaster.mapper;

import com.testmasterapi.domain.user.JwtClaimNames;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.UserRoles;
import com.testmasterapi.domain.user.data.UserOwnerData;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.model.User.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public final class UserMapper {
    public UserData toUserData(User user) {
        return new UserData(
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

    public UserOwnerData toOwner(User user) {
        return new UserOwnerData(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User map(UserCreateRequest request, String activationLink) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();

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

    public static User map(DecodedJWT jwt) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();

        user.setEmail(jwt.getClaim(JwtClaimNames.EMAIL).asString());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return user;
    }
}