package com.testmaster.model.GroupModel;

import com.testmasterapi.domain.user.UserGroupsId;
import com.testmaster.model.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupsUserModel {
    @EmbeddedId
    private UserGroupsId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private GroupModel group;
}