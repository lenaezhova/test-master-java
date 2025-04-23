package com.testmasterapi.domain.test.response;

import com.testmasterapi.domain.test.data.TestData;

import java.util.List;

public record TestsResponse(
        List<TestData> data
) {
}
