package com.testmaster.repository;

import com.testmaster.model.AnswerVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerVariantRepository extends JpaRepository<AnswerVariant, Long> {
}
