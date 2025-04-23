package com.testmaster.model.User;

import com.testmaster.model.Group;
import com.testmasterapi.domain.user.UserGroupId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroup {
    @EmbeddedId
    private UserGroupId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;
}