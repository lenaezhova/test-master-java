package com.testmasterapi.domain.group.response;

import com.testmasterapi.domain.group.data.GroupsUserData;

import java.util.List;

public record GroupsUsersResponse(
        List<GroupsUserData> data
) {
}
