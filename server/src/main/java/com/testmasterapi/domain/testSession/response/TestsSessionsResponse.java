package com.testmasterapi.domain.testSession.response;

import com.testmasterapi.domain.testSession.data.TestSessionData;

import java.util.List;

public record TestsSessionsResponse(
        List<TestSessionData> content
) {
}
