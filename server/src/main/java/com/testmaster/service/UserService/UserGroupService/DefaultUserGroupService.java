package com.testmaster.service.UserService.UserGroupService;

import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.GroupMapper;
import com.testmaster.model.Group;
import com.testmaster.model.User.User;
import com.testmaster.model.User.UserGroup;
import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmaster.repository.UserGroupRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.UserGroupsAddRequest;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.user.UserGroupId;
import com.testmasterapi.domain.user.data.UserData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.LongSupplier;

@Service
@RequiredArgsConstructor
public class DefaultUserGroupService implements UserGroupService {
    private final GroupMapper groupMapper;

    private final UserGroupRepository groupUserRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @NotNull
    @Override
    public PageData<GroupsUserData> getAllGroups(Long userId, @NotNull Pageable pageable) {
        var content = groupUserRepository.findAllByUserId(userId, pageable)
                .stream()
                .map(groupMapper::toGroupsUser)
                .toList();

        LongSupplier total = () -> groupUserRepository.countAllByUserId(userId);

        Page<GroupsUserData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @Transactional
    @Override
    public void addGroup(Long userId, Long groupId, UserGroupsAddRequest request) {
        if (groupUserRepository.existsByUser_Id(userId)) {
            throw new ClientException("Пользователь уже состоит в группе с таким идентификатором", HttpStatus.CONFLICT.value());
        }

        Group group = this.getGroup(groupId);
        User user = this.getUser(userId);
        UserGroupId id = new UserGroupId( user.getId(), group.getId() );

        UserGroup userGroups = new UserGroup();
        userGroups.setGroup(group);
        userGroups.setUser(user);
        userGroups.setId(id);
        groupUserRepository.save(userGroups);
    }

    @Override
    public void deleteGroup(Long userId, Long groupId) {
        int deleted = groupUserRepository.delete(userId, groupId);
        if (deleted == 0) {
            throw new NotFoundException("Пользователь (группа) не найдена");
        }
    }

    private Group getGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Группа не найдена"));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }
}
