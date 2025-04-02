package com.testmaster.repository.RoleRepository;

import com.testmaster.model.RoleModel.RolesUserModel;
import com.testmaster.model.UserModel.UserRolesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesUserRepository extends JpaRepository<RolesUserModel, UserRolesId> {
}
