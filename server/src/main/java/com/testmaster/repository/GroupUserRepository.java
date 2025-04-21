package com.testmaster.repository;

import com.testmaster.model.Group.Group;
import com.testmaster.model.User;
import com.testmaster.model.Group.GroupUser;
import com.testmasterapi.domain.group.GroupUserId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, GroupUserId> {
    boolean existsByUser_Id(Long userId);

    @Query("select gu.user from GroupUser gu where gu.id.groupId = :groupId")
    List<User> findAllUsersByGroupId(@Param("groupId") Long groupId);

    @Query("select gu.group from GroupUser gu where gu.id.userId = :userId")
    List<Group> findAllGroupsByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("delete from GroupUser groupsUsers where groupsUsers.id.userId = :userId and groupsUsers.id.groupId = :groupId")
    int deleteUserFromGroup(
            @Param("userId") Long userId,
            @Param("groupId") Long groupId
    );
}
