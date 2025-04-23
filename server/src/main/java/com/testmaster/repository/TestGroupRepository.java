package com.testmaster.repository;

import com.testmaster.model.Test.Test;
import com.testmaster.model.Test.TestGroup;
import com.testmasterapi.domain.test.TestGroupId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestGroupRepository extends JpaRepository<TestGroup, TestGroupId> {
    boolean existsByTest_Id(Long testId);

    @Query("select gt.test from TestGroup gt where gt.id.groupId = :groupId and gt.test.deleted = false")
    List<Test> findAllByGroupId(@Param("groupId") Long groupId);

    @Transactional
    @Modifying
    @Query("delete from TestGroup gt where gt.id.testId = :testId and gt.id.groupId = :groupId and gt.test.deleted = false")
    int delete(
            @Param("testId") Long testId,
            @Param("groupId") Long groupId
    );
}
