package com.testmaster.repository.AnswerRepository;

import com.testmaster.model.Answer;
import com.testmaster.model.AnswerTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {
    @Modifying
    @Query("delete from Answer a where a.id = :id")
    int delete(@Param("id") Long id);

    @Query("""
        select a from Answer a
        where a.question.id = :questionId and a.question.softDeleted = false
    """)
    List<Answer> findAllByQuestionId(@Param("questionId") Long questionId);

    @Modifying
    @Query("""
        delete from Answer a
        where a.question.id = :questionId and a.question.softDeleted = false
    """)
    int deleteAllByQuestionId(@Param("questionId") Long questionId);
}
