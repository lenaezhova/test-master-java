package com.testmasterapi.domain.user.response;

import com.testmasterapi.domain.user.data.UserGroupsData;

import java.util.List;

public record UsersGroupsResponse(
        List<UserGroupsData> content
) {
}
