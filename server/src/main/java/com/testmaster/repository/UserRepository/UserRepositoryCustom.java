package com.testmaster.repository.UserRepository;

import com.testmasterapi.domain.user.request.UserUpdateRequest;

public interface UserRepositoryCustom {

    int update(Long userId, UserUpdateRequest request);
}