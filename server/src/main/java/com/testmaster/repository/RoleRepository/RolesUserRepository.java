package com.testmaster.repository.RoleRepository;

import com.testmaster.model.RoleModel.RolesUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesUserRepository extends JpaRepository<RolesUserModel, Long> {
}
