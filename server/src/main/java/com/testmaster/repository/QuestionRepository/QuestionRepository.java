package com.testmaster.repository.QuestionRepository;

import com.testmaster.model.Question;
import com.testmaster.model.TestSession;
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
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    @NotNull
    @Query("""
        select q from Question q
        where (:showOnlySoftDeleted is null or q.softDeleted = :showOnlySoftDeleted)
    """)
    List<Question> findAllQuestions(
            @Param("showOnlySoftDeleted") Boolean showOnlySoftDeleted,
            Pageable pageable
    );

    @Query("""
        select count(q) from Question q
        where (:showOnlySoftDeleted is null or q.softDeleted = :showOnlySoftDeleted)
    """)
    long countAllQuestions(@Param("showOnlySoftDeleted") Boolean showOnlySoftDeleted);

    @Query("""
        select q from Question q
        where q.test.id = :testId and
              (:showQuestionSoftDeleted is true or q.softDeleted = false )
    """)
    List<Question> findAllByTestId(
            @Param("testId") Long testId,
            @Param("showQuestionSoftDeleted") Boolean showQuestionSoftDeleted
    );

    @NotNull
    @Query("""
        select q from Question q
        where q.id = :id and q.softDeleted = false
    """)
    Optional<Question> findById(@NotNull @Param("id") Long id);

    @Modifying
    @Query("""
        delete from Question q
        where q.id = :id and q.softDeleted = false
    """)
    int delete(@Param("id") Long id);

    @Modifying
    @Query("""
        delete from Question q
        where q.id in :ids and q.softDeleted = false
    """)
    void deleteByIds(@Param("ids") List<Long> ids);

    @Modifying
    @Query("delete from Question q where q.test.id = :testId")
    int deleteAllByTestId(@Param("testId") Long testId);
}
