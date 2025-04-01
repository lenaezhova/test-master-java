package com.testmaster.repository.RoleRepository;

import com.testmaster.model.RoleModel.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
}
