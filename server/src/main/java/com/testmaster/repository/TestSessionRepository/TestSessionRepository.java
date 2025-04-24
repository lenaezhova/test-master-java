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
        where (:showOnlyTestDeleted is null or ts.test.deleted = :showOnlyTestDeleted)
    """)
    List<TestSession> findAllTestSessions(
            @Param("showOnlyTestDeleted") Boolean showOnlyTestDeleted,
            Pageable pageable
    );

    @Query("""
        select count(ts) from TestSession ts
        where (:showOnlyTestDeleted is null or ts.test.deleted = :showOnlyTestDeleted)
    """)
    long countAllTestSessions(@Param("showOnlyTestDeleted") Boolean showOnlyTestDeleted);

    @NotNull
    @Query("""
        select ts from TestSession ts
        where ts.test.id = :testId and
              (:showUserDeleted is true or ts.user.deleted = false)
    """)
    List<TestSession> findAllByTestId(
            @Param("testId") Long testId,
            @Param("showUserDeleted") Boolean showUserDeleted,
            Pageable pageable
    );

    @Query("""
        select count(ts) from TestSession ts
        where ts.test.id = :testId and
              (:showUserDeleted is true or ts.user.deleted = false)
    """)
    long countAllByTestId(
            @Param("testId") Long testId,
            @Param("showUserDeleted") Boolean showUserDeleted
    );

    @Query("""
        select ts from TestSession ts
        where ts.user.id = :userId and
              (:showTestDeleted is true or ts.test.deleted = false)
    """)
    List<TestSession> findAllByUserId(
            @Param("userId") Long userId,
            @Param("showTestDeleted") Boolean showTestDeleted,
            Pageable pageable
    );

    @Query("""
        select count(ts) from TestSession ts
        where ts.user.id = :userId and
              (:showTestDeleted is true or ts.test.deleted = false)
    """)
    long countAllByUserId(
            @Param("userId") Long userId,
            @Param("showTestDeleted") Boolean showTestDeleted
    );

    @Modifying
    @Query("delete from TestSession ts where ts.id = :id")
    int delete(@Param("id") Long id);
}
