package com.testmaster.mapper;
import com.testmaster.model.User.User;
import com.testmasterapi.domain.group.data.GroupData;
import com.testmaster.model.Group;
import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class GroupMapper {
    @Autowired
    private UserMapper userMapper;

    public GroupData toData(Group group) {
        var groupData = new GroupData();
        groupData.setId(group.getId());
        groupData.setTitle(group.getTitle());
        groupData.setOwner(userMapper.toOwner(group.getOwner()));
        return groupData;
    }

    public Group toEntity(GroupCreateRequest request, User user) {
        var entity = new Group();

        entity.setTitle(request.title());
        entity.setOwner(user);

        return entity;
    }

    public GroupsUserData toGroupsUser(Group group) {
        var groupsUserData = new GroupsUserData();
        groupsUserData.setId(group.getId());
        groupsUserData.setTitle(group.getTitle());
        return groupsUserData;
    }
}
