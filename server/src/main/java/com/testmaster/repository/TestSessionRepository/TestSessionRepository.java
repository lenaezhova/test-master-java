package com.testmaster.repository.TestSessionRepository;

import com.testmaster.model.Question;
import com.testmaster.model.TestSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSession, Long>, TestSessionRepositoryCustom {
    @Query("""
        select ts from TestSession ts
        where ts.user.id = :userId and ts.user.deleted = false
    """)
    List<TestSession> findAllByUserId(@Param("userId") Long userId);

    @Query("""
        select ts from TestSession ts
        where ts.test.id = :testId and ts.test.deleted = false
    """)
    List<TestSession> findAllByTestId(@Param("testId") Long testId);

    @Modifying
    @Query("delete from TestSession ts where ts.id = :id")
    int delete(@Param("id") Long id);
}
