package com.testmaster.repository.TestRepository;

import com.testmasterapi.domain.test.request.TestUpdateRequest;

public interface TestRepositoryCustom {

    int update(Long testId, TestUpdateRequest request);
}