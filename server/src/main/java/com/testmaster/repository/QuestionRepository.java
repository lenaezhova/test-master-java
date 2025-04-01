package com.testmaster.repository;

import com.testmaster.model.UserModel.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel, Long> {
}
