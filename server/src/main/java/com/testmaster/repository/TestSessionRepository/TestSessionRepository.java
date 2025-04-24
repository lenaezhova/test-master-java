package com.testmaster.repository.TestSessionRepository;

import com.testmaster.model.Question;
import com.testmaster.model.TestSession;
import com.testmaster.model.User.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSession, Long>, TestSessionRepositoryCustom {
    @NotNull
    @Query("""
        select ts from TestSession ts
        where (:showDeleted is null or ts.test.deleted = :showDeleted)
    """)
    List<TestSession> findAllTestSessions(
            @Param("showDeleted") Boolean showDeleted,
            Pageable pageable
    );

    @Query("""
        select count(ts) from TestSession ts
        where (:showDeleted is null or ts.test.deleted = :showDeleted)
    """)
    long countAllTestSessions(@Param("showDeleted") Boolean showDeleted);

    @NotNull
    @Query("""
        select ts from TestSession ts
        where ts.test.id = :testId and
              (:showDeleted is null or ts.test.deleted = :showDeleted)
    """)
    List<TestSession> findAllByTestId(
            @Param("testId") Long testId,
            @Param("showDeleted") Boolean showDeleted,
            Pageable pageable
    );

    @Query("""
        select count(ts) from TestSession ts
        where ts.test.id = :testId and
              (:showDeleted is null or ts.test.deleted = :showDeleted)
    """)
    long countAllByTestId(
            @Param("testId") Long testId,
            @Param("showDeleted") Boolean showDeleted
    );

    @Query("""
        select ts from TestSession ts
        where ts.user.id = :userId and
              (:showDeleted is null or ts.user.deleted = :showDeleted)
    """)
    List<TestSession> findAllByUserId(
            @Param("userId") Long userId,
            @Param("showDeleted") Boolean showDeleted,
            Pageable pageable
    );

    @Query("""
        select count(ts) from TestSession ts
        where ts.user.id = :userId and
              (:showDeleted is null or ts.user.deleted = :showDeleted)
    """)
    long countAllByUserId(
            @Param("userId") Long userId,
            @Param("showDeleted") Boolean showDeleted
    );

    @Modifying
    @Query("delete from TestSession ts where ts.id = :id")
    int delete(@Param("id") Long id);
}
