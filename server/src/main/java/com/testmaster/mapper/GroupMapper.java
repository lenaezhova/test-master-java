package com.testmaster.mapper;
import com.testmasterapi.domain.group.data.GroupData;
import com.testmaster.model.Group.Group;
import com.testmasterapi.domain.user.data.UserGroupsData;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class GroupMapper {
    @Autowired
    private UserMapper userMapper;

    public GroupData toGroupData(Group group) {
        var groupData = new GroupData();
        groupData.setId(group.getId());
        groupData.setTitle(group.getTitle());
        groupData.setOwner(userMapper.toOwner(group.getOwner()));
        return groupData;
    }

    public UserGroupsData toUserGroupsData(Group group) {
        var userGroupsData = new UserGroupsData();
        userGroupsData.setId(group.getId());
        userGroupsData.setTitle(group.getTitle());
        return userGroupsData;
    }
}
