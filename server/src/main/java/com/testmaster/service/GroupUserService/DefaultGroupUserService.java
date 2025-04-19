package com.testmaster.service.GroupUserService;

import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.GroupMapper;
import com.testmaster.mapper.UserMapper;
import com.testmaster.model.Group.Group;
import com.testmaster.model.User;
import com.testmaster.model.Group.GroupUser;
import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmaster.repository.GroupUserRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.GroupUsersAddRequest;
import com.testmasterapi.domain.group.GroupUserId;
import com.testmasterapi.domain.user.data.UserGroupsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGroupUserService implements GroupUserService {
    private final UserMapper userMapper;
    private final GroupMapper groupMapper;

    private final GroupUserRepository groupUserRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public List<UserGroupsData> getAllUsersGroup(Long userId) {
        return groupUserRepository
                .findAllUsersByGroupId(userId)
                .stream()
                .map(userMapper::toUserGroupsData)
                .toList();
    }

    @Override
    public List<GroupsUserData> getAllGroupsUser(Long userId) {
        return groupUserRepository
                .findAllGroupsByUserId(userId)
                .stream()
                .map(groupMapper::toGroupsUserData)
                .toList();
    }

    @Transactional
    @Override
    public void add(Long groupId, GroupUsersAddRequest request) {
        Long userId = request.userId();

        if (groupUserRepository.existsByUser_Id(userId)) {
            throw new ClientException("Пользователь уже состоит в группе", HttpStatus.CONFLICT.value());
        }
        Group group = groupRepository
                .findById(groupId)
                .orElseThrow(NotFoundException::new);

        User user = userRepository
                .findById(userId)
                .orElseThrow(NotFoundException::new);

        GroupUserId id = new GroupUserId(
                user.getId(),
                group.getId()
        );

        GroupUser userGroups = new GroupUser();
        userGroups.setId(id);
        userGroups.setGroup(group);
        userGroups.setUser(user);
        groupUserRepository.save(userGroups);
    }

    @Override
    public void delete(Long groupId, Long userId) {
        int deleted = groupUserRepository.deleteUserFromGroup(userId, groupId);
        if (deleted == 0) {
            throw new NotFoundException();
        }
    }
}
