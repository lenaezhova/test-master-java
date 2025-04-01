package com.testmaster.model.RoleModel;

import com.testmaster.model.UserModel.UserModel;
import com.testmaster.model.UserModel.UserRolesId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesUserModel {
    @EmbeddedId
    private UserRolesId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleModel role;
}