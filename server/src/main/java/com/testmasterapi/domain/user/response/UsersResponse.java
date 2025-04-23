package com.testmasterapi.domain.user.response;

import com.testmasterapi.domain.user.data.UserData;

import java.util.List;

public record UsersResponse(
        List<UserData> data
) {
}
