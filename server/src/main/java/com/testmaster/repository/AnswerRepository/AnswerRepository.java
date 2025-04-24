package com.testmaster.repository.AnswerRepository;

import com.testmaster.model.Answer;
import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.User.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {

    @Query("""
        select a from Answer a
        where a.question.id = :questionId and
             (:showDeletedQuestion is null or a.question.softDeleted = :showDeletedQuestion)
    """)
    List<Answer> findAllByQuestionId(
            @Param("questionId") Long questionId,
            @Param("showDeletedQuestion") Boolean showDeletedQuestion,
            Pageable pageable
    );

    @Query("""
        select count(a) from Answer a
        where a.question.id = :questionId and
             (:showDeletedQuestion is null or a.question.softDeleted = :showDeletedQuestion)
    """)
    long countAllByQuestionId(
            @Param("questionId") Long questionId,
            @Param("showDeletedQuestion") Boolean showDeletedQuestion
    );

    @Modifying
    @Query("delete from Answer a where a.id = :id")
    int delete(@Param("id") Long id);

    @Modifying
    @Query("""
        delete from Answer a
        where a.testSession.id = :testSessionId
    """)
    int deleteAllByTestSessionId(@Param("testSessionId") Long testSessionId);

    @Modifying
    @Query("""
        delete from Answer a
        where a.question.id = :questionId and a.testSession.id = :testSessionId
    """)
    int deleteAllByQuestionIdAndTestSessionId(@Param("testSessionId") Long testSessionId, @Param("questionId") Long questionId);

    @Modifying
    @Query("""
        delete from Answer a
        where a.question.id = :questionId
    """)
    int deleteAllByQuestionId(@Param("questionId") Long questionId);

    @Modifying
    @Query("""
        delete from Answer a
        where a.question.id in :ids
    """)
    void deleteAllByQuestionIds(@Param("ids") List<Long> ids);
}
