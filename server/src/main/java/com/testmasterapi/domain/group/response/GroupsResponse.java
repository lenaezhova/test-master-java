package com.testmasterapi.domain.group.response;

import com.testmasterapi.domain.group.data.GroupData;

import java.util.List;

public record GroupsResponse(
        List<GroupData> content
) {
}
