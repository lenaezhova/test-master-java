package com.testmaster.repository;

import com.testmaster.model.Group.Group;
import com.testmaster.model.User.UserGroups;
import com.testmasterapi.domain.user.UserGroupsId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupsRepository extends JpaRepository<UserGroups, UserGroupsId> {
    boolean existsByGroup_Id(Long groupId);

    @Query("select ug.group from UserGroups ug where ug.id.userId = :userId")
    List<Group> findAllByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("delete from UserGroups userGroups where userGroups.id.userId = :userId and userGroups.id.groupId = :groupId")
    int delete(
            @Param("userId") Long userId,
            @Param("groupId") Long groupId
    );
}
