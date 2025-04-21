package com.testmaster.repository.AnswerVariantRepository;

import com.testmaster.model.AnswerVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerVariantRepository extends JpaRepository<AnswerVariant, Long>, AnswerVariantRepositoryCustom {
    List<AnswerVariant> findAllByQuestionId(Long questionId);

    @Modifying
    @Query("delete from AnswerVariant aw where aw.id = :id")
    int delete(@Param("id") Long id);
}
