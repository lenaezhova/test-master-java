package com.testmaster.repository.QuestionRepository;

import com.testmaster.model.Question;
import com.testmaster.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    @Query("""
        select q from Question q
        where q.softDeleted = false
    """)
    List<Question> findAllQuestions();

    @Query("""
        select q from Question q
        where q.test.id = :testId and q.softDeleted = false
    """)
    List<Question> findAllQuestionsByTestId(@Param("testId") Long testId);

    @Query("""
        select q from Question q
        where q.id = :questionId and q.softDeleted = false
    """)
    Optional<Question> findQuestionById(@Param("questionId") Long questionId);

    @Modifying
    @Query("delete from Question q where q.id = :id and q.softDeleted = false")
    int delete(@Param("id") Long id);

    @Modifying
    @Query("delete from Question q where q.test.id = :testId")
    int deleteAllQuestionByTestId(@Param("testId") Long testId);
}
