package com.testmaster.repository;

import com.testmaster.model.TypeQuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeQuestionRepository extends JpaRepository<TypeQuestionModel, Long> {
}
