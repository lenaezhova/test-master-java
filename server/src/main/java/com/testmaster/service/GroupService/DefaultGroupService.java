package com.testmaster.service.GroupService;

import com.testmaster.exeption.NotFoundException;
import com.testmasterapi.domain.group.data.GroupData;
import com.testmaster.mapper.GroupMapper;
import com.testmaster.model.Group.Group;
import com.testmaster.model.User;
import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGroupService implements GroupService {
    private final GroupMapper groupMapper;

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public List<GroupData> getAll() {
        return groupRepository
                .findAll()
                .stream()
                .map(groupMapper::toGroupData)
                .toList();
    }

    @Override
    public GroupData getOne(Long id) {
        Group groupModel = groupRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Группа не найдена"));

        return groupMapper.toGroupData(groupModel);
    }

    @NotNull
    @Transactional
    @Override
    public GroupData create(@NotNull GroupCreateRequest request) {
        var userId = this.getUserId();

        User user = userRepository
                .findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким идентификатором не найден"));

        Group group = groupMapper.toEntity(request, user);
        groupRepository.save(group);
        return groupMapper.toGroupData(group);
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
