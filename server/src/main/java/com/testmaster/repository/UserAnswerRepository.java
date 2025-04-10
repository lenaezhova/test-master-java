package com.testmaster.repository;

import com.testmaster.model.UserAnswerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswerModel, Long> {
}
