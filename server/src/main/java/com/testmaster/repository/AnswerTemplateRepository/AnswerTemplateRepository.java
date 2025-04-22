package com.testmaster.repository.AnswerTemplateRepository;

import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerTemplateRepository extends JpaRepository<AnswerTemplate, Long>, AnswerTemplateRepositoryCustom {
    @Query("""
        select at from AnswerTemplate at
        where at.question.id = :questionId and at.question.softDeleted = false
    """)
    List<AnswerTemplate> findAllByQuestionId(@Param("questionId") Long questionId);

    @Modifying
    @Query("delete from AnswerTemplate at where at.id = :id")
    int delete(@Param("id") Long id);

    @Modifying
    @Query("""
        delete from AnswerTemplate at
        where at.question.id = :questionId and at.question.softDeleted = false
    """)
    int deleteAllByQuestionId(@Param("questionId") Long questionId);
}
