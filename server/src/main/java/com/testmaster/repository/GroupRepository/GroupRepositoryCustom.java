package com.testmaster.repository.GroupRepository;

import com.testmasterapi.domain.group.request.GroupUpdateRequest;

public interface GroupRepositoryCustom {

    int update(Long groupId, GroupUpdateRequest request);
}