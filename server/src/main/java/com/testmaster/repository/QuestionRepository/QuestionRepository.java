package com.testmaster.repository.QuestionRepository;

import com.testmaster.model.Question;
import org.jetbrains.annotations.NotNull;
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
        where q.softDeleted = false
    """)
    List<Question> findAll();

    @Query("""
        select q from Question q
        where q.test.id = :testId and q.softDeleted = false
    """)
    List<Question> findAllByTestId(@Param("testId") Long testId);

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
