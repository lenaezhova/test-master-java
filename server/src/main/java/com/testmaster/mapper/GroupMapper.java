package com.testmaster.mapper;
import com.testmaster.model.Test.Test;
import com.testmaster.model.User.User;
import com.testmasterapi.domain.group.data.BaseGroupData;
import com.testmasterapi.domain.group.data.GroupData;
import com.testmaster.model.Group;
import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.test.data.BaseTestData;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class GroupMapper {
    @Autowired
    private UserMapper userMapper;

    public GroupData toData(Group group) {
        var groupData = new GroupData();

        fillGroup(groupData, group);
        groupData.setOwner(userMapper.toOwner(group.getOwner()));
        return groupData;
    }

    public GroupsUserData toGroupsUser(Group group) {
        var groupsUserData = new GroupsUserData();

        fillGroup(groupsUserData, group);

        return groupsUserData;
    }

    public Group toEntity(GroupCreateRequest request, User user) {
        var entity = new Group();

        entity.setTitle(request.title());
        entity.setOwner(user);

        return entity;
    }

    private void fillGroup(BaseGroupData data, Group group) {
        data.setId(group.getId());
        data.setTitle(group.getTitle());
    }
}
