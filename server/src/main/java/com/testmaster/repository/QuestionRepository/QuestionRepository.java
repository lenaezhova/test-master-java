package com.testmaster.repository.QuestionRepository;

import com.testmaster.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    @Modifying
    @Query("delete from Question q where q.id = :id")
    int delete(@Param("id") Long id);
}
