package com.testmaster.service.UserService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.TestSessionMapper;
import com.testmaster.mapper.UserMapper;
import com.testmaster.model.User.User;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.user.CustomUserDetails;
import com.testmasterapi.domain.user.data.UserData;
import com.testmasterapi.domain.user.request.UserUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    protected final UserRepository userRepository;
    protected final TestSessionRepository testSessionRepository;

    private final UserMapper userMapper;
    private final TestSessionMapper testSessionMapper;

    @Override
    public List<UserData> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toData)
                .toList();
    }

    @Override
    public List<TestSessionData> getAllSessions(Long id) {
        return testSessionRepository.findAllByUserId(id)
                .stream()
                .map(testSessionMapper::toData)
                .toList();
    }

    @Override
    public UserData getOne(Long id) {
        return userMapper.toData(this.getUser(id));
    }

    @Override
    public UserData getCurrent() {
        return userMapper.toData(this.getCurrentUser());
    }

    @Override
    @Transactional
    public void update(Long userId, UserUpdateRequest updateRequest) {
        int updated = userRepository.update(userId, updateRequest);
        if (updated == 0) {
            throw new NotFoundException("Пользователь не найден");
        }
    }
    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private User getCurrentUser() {
        var currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.getUser(currentUser.getId());
    }
}
