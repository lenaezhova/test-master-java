package com.testmaster.mapper;

import com.testmasterapi.domain.user.JwtClaimNames;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.UserRoles;
import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.data.UserOwnerData;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public final class UserMapper {
    public UserData toUserData(User user) {
        var data = new UserData();
        data.setId(user.getId());
        data.setName(user.getName());
        data.setEmail(user.getEmail());
        data.setActivationLink(user.getActivationLink());
        data.setIsActivate(user.getIsActivate());
        data.setRoles(user.getRoles());
        data.setCreatedAt(user.getCreatedAt());
        data.setUpdatedAt(user.getUpdatedAt());

        return data;
    }

    public UserOwnerData toOwner(User user) {
        UserOwnerData data = new UserOwnerData();
        data.setId(user.getId());
        data.setName(user.getName());
        data.setEmail(user.getEmail());

        return data;
    }

    public UserGroupsData toUserGroupsData(User user) {
        UserGroupsData data = new UserGroupsData();
        data.setId(user.getId());
        data.setName(user.getName());
        data.setEmail(user.getEmail());

        return data;
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