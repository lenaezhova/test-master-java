package com.testmaster.model.Group;

import com.testmaster.model.User.User;
import com.testmasterapi.domain.group.GroupUserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groups_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupUser {
    @EmbeddedId
    private GroupUserId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;
}