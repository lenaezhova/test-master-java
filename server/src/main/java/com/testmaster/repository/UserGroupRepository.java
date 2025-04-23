package com.testmaster.repository;

import com.testmaster.model.Group;
import com.testmaster.model.User.User;
import com.testmaster.model.User.UserGroup;
import com.testmasterapi.domain.user.UserGroupId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {
    boolean existsByUser_Id(Long userId);

    @Query("select gu.user from UserGroup gu where gu.id.groupId = :groupId")
    List<User> findAllByGroupId(@Param("groupId") Long groupId);

    @Query("select gu.group from UserGroup gu where gu.id.userId = :userId")
    List<Group> findAllByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("""
              delete from UserGroup groupsUsers
              where groupsUsers.id.userId = :userId and groupsUsers.id.groupId = :groupId
           """)
    int delete(
            @Param("userId") Long userId,
            @Param("groupId") Long groupId
    );
}
