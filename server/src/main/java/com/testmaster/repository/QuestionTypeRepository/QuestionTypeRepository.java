package com.testmaster.repository.QuestionTypeRepository;

import com.testmaster.model.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long>, QuestionTypeRepositoryCustom {
    @Modifying
    @Query("delete from QuestionType qt where qt.id = :id")
    int delete(@Param("id") Long id);
}
