package com.testmaster.model.User;

import com.testmaster.model.Group.Group;
import com.testmasterapi.domain.user.UserGroupsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroups {
    @EmbeddedId
    private UserGroupsId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;
}