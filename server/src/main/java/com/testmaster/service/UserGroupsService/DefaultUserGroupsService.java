package com.testmaster.service.UserGroupsService;

import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.GroupMapper;
import com.testmaster.model.Group.Group;
import com.testmaster.model.User.User;
import com.testmaster.model.User.UserGroups;
import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmaster.repository.UserGroupsRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.user.CustomUserDetails;
import com.testmasterapi.domain.user.UserGroupsId;
import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.request.UserGroupsAddRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultUserGroupsService implements UserGroupsService {
    private final GroupMapper groupMapper;

    private final UserGroupsRepository userGroupsRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public List<UserGroupsData> getAll() {
        return userGroupsRepository
                .findAllByUserId(this.getUserId())
                .stream()
                .map(groupMapper::toUserGroupsData)
                .toList();
    }

    @Override
    @Transactional
    public UserGroupsData add(Long groupId) {
        Long userId = this.getUserId();

        if (userGroupsRepository.existsByGroup_Id(groupId)) {
            throw new ClientException("Пользователь уже состоит в группе", HttpStatus.CONFLICT.value());
        }
        Group group = groupRepository
                .findById(groupId)
                .orElseThrow(NotFoundException::new);

        User user = userRepository
                .findById(userId)
                .orElseThrow(NotFoundException::new);

        UserGroupsId id = new UserGroupsId(
                user.getId(),
                group.getId()
        );

        UserGroups userGroups = new UserGroups();
        userGroups.setId(id);
        userGroups.setGroup(group);
        userGroups.setUser(user);
        userGroupsRepository.save(userGroups);

        System.out.println("6");

        return groupMapper.toUserGroupsData(group);
    }

    @Override
    public void delete(Long groupId) {
        int deleted = userGroupsRepository.delete(this.getUserId(), groupId);
        if (deleted == 0) {
            throw new NotFoundException();
        }
    }

    private Long getUserId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUserDetails.getId();
    }
}
