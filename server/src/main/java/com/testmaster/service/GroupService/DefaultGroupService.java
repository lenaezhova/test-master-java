package com.testmaster.service.GroupService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.TestMapper;
import com.testmaster.mapper.UserMapper;
import com.testmaster.repository.TestGroupRepository;
import com.testmaster.repository.UserGroupRepository;
import com.testmasterapi.domain.group.data.GroupData;
import com.testmaster.mapper.GroupMapper;
import com.testmaster.model.Group;
import com.testmaster.model.User.User;
import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.data.TestGroupsData;
import com.testmasterapi.domain.user.CustomUserDetails;
import com.testmasterapi.domain.user.data.UserGroupsData;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.LongSupplier;

@Service
@RequiredArgsConstructor
public class DefaultGroupService implements GroupService {
    private final GroupMapper groupMapper;
    private final UserMapper userMapper;
    private final TestMapper testMapper;

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final TestGroupRepository testGroupRepository;

    @NotNull
    @Override
    public PageData<GroupData> getAll(@NotNull Pageable pageable) {
        var content = groupRepository.findAllGroups(pageable)
                .stream()
                .map(groupMapper::toData)
                .toList();

        LongSupplier total = groupRepository::countAllGroups;

        Page<GroupData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @NotNull
    @Override
    public PageData<UserGroupsData> getAllUsers(Long groupId, Boolean showDeleted, @NotNull Pageable pageable) {
        var content = userGroupRepository.findAllByGroupId(groupId, showDeleted, pageable)
                .stream()
                .map(userMapper::toUserGroups)
                .toList();

        LongSupplier total = () -> userGroupRepository.countAllByGroupId(groupId, showDeleted);

        Page<UserGroupsData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @Override
    @NotNull
    public PageData<TestGroupsData> getAllTests(Long groupId, Boolean showDeleted, @NotNull Pageable pageable) {
        var content = testGroupRepository.findAllByGroupId(groupId, showDeleted, pageable)
                .stream()
                .map(testMapper::toTestGroups)
                .toList();

        LongSupplier total = () -> testGroupRepository.countAllByGroupId(groupId, showDeleted);

        Page<TestGroupsData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @Override
    public GroupData getOne(Long id) {
        Group groupModel = groupRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Группа не найдена"));

        return groupMapper.toData(groupModel);
    }

    @NotNull
    @Transactional
    @Override
    public GroupData create(@NotNull GroupCreateRequest request) {
        var userId = this.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким идентификатором не найден"));

        Group group = groupMapper.toEntity(request, user);
        groupRepository.save(group);
        return groupMapper.toData(group);
    }

    @Override
    @Transactional
    public void update(Long groupId, GroupUpdateRequest request) {
        int updated = groupRepository.update(groupId, request);
        if (updated == 0) {
            throw new NotFoundException("Группа не найдена");
        }
    }

    @Override
    @Transactional
    public void delete(Long groupId) {
        int deleted = groupRepository.delete(groupId);
        if (deleted == 0) {
            throw new NotFoundException("Группа не найдена");
        }
    }

    private Long getUserId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUserDetails.getId();
    }
}
