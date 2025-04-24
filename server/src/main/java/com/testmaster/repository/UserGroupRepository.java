package com.testmaster.repository;

import com.testmaster.model.Group;
import com.testmaster.model.TestSession;
import com.testmaster.model.User.User;
import com.testmaster.model.User.UserGroup;
import com.testmasterapi.domain.user.UserGroupId;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {
    boolean existsByUser_Id(Long userId);

    @NotNull
    @Query("""
        select gu.user from UserGroup gu
        where gu.id.groupId = :groupId and
             (:showUserDeleted is true or gu.user.deleted = false)
    """)
    List<User> findAllByGroupId(
            @Param("groupId") Long groupId,
            @Param("showUserDeleted") Boolean showUserDeleted,
            Pageable pageable
    );

    @Query("""
        select count(gu.user) from UserGroup gu
        where gu.id.groupId = :groupId and
             (:showUserDeleted is true or gu.user.deleted = false)
    """)
    long countAllByGroupId(
            @Param("groupId") Long groupId,
            @Param("showUserDeleted") Boolean showUserDeleted
    );

    @NotNull
    @Query("""
        select gu.group from UserGroup gu
        where gu.id.userId = :userId
    """)
    List<Group> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("""
        select count(gu.group) from UserGroup gu
        where gu.id.userId = :userId
    """)
    long countAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("""
        delete from UserGroup ug
        where ug.id.userId = :userId and ug.id.groupId = :groupId
    """)
    int delete(
            @Param("userId") Long userId,
            @Param("groupId") Long groupId
    );
}
