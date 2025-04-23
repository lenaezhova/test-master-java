package com.testmasterapi.domain.test.response;

import com.testmasterapi.domain.test.data.TestGroupsData;

import java.util.List;

public record TestsGroupsResponse(
        List<TestGroupsData> data
) {
}
