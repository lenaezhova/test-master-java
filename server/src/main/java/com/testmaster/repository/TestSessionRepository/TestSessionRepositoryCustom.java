package com.testmaster.repository.TestSessionRepository;

import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;

public interface TestSessionRepositoryCustom {

    int update(Long testSessionId, TestSessionUpdateRequest request);
}