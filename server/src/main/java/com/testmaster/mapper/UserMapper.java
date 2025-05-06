package com.testmaster.mapper;

import com.testmasterapi.domain.user.JwtClaimNames;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.UserRoles;
import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.data.UserOwnerData;
import com.testmasterapi.domain.user.data.UserTestSessionData;
import com.testmasterapi.domain.user.request.UserCreateRequest;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.testmaster.model.User.User;
import com.testmasterapi.domain.user.request.UserUpdateCurrentRequest;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public final class UserMapper {
    public UserData toData(User user) {
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
        var data = new UserOwnerData();
        data.setId(user.getId());
        data.setName(user.getName());
        data.setEmail(user.getEmail());

        return data;
    }

    public UserTestSessionData toTestSession(User user) {
        var data = new UserTestSessionData();

        data.setId(user.getId());
        data.setName(user.getName());
        data.setEmail(user.getEmail());

        return data;
    }

    public UserGroupsData toUserGroups(User user) {
        var data = new UserGroupsData();
        data.setId(user.getId());
        data.setName(user.getName());
        data.setEmail(user.getEmail());

        return data;
    }

    public UserUpdateRequest currentToUserUpdateReq(UserUpdateCurrentRequest request) {
        var data = new UserUpdateRequest();
        data.setName(request.getName());
        data.setPassword(request.getPassword());
        data.setDeleted(request.getDeleted());

        return data;
    }

    public User toEntity(UserCreateRequest request, String activationLink) {
        var entity = new User();
        LocalDateTime now = LocalDateTime.now();

        entity.setEmail(request.email());
        entity.setName(request.name());
        entity.setPassword(request.password());
        entity.setActivationLink(activationLink);
        entity.setIsActivate(false);
        entity.setRoles(Set.of(UserRoles.USER));
        entity.setCreatedAt(now);

        return entity;
    }

    public static User map(DecodedJWT jwt) {
        var user = new User();

        user.setEmail(jwt.getClaim(JwtClaimNames.EMAIL).asString());

        return user;
    }
}