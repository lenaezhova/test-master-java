package com.testmaster.repository.GroupRepository;

import com.testmaster.model.GroupModel.GroupsUserModel;
import com.testmasterapi.domain.user.UserGroupsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsUserRepository extends JpaRepository<GroupsUserModel, UserGroupsId> {
}
