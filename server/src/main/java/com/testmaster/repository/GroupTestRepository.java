package com.testmaster.repository;

import com.testmaster.model.Group.GroupTest;
import com.testmaster.model.Test;
import com.testmaster.model.User;
import com.testmasterapi.domain.group.GroupTestId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupTestRepository extends JpaRepository<GroupTest, GroupTestId> {
    boolean existsByTest_Id(Long testId);

    @Query("select gt.test from GroupTest gt where gt.id.groupId = :groupId")
    List<Test> findAllTestsByGroupId(@Param("groupId") Long groupId);

    @Transactional
    @Modifying
    @Query("delete from GroupTest groupTest where groupTest.id.testId = :testId and groupTest.id.groupId = :groupId")
    int delete(
            @Param("testId") Long testId,
            @Param("groupId") Long groupId
    );
}
