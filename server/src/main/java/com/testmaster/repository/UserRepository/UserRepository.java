package com.testmaster.repository.UserRepository;

import com.testmaster.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
    Optional<User> findByActivationLink(String activationLink);
    boolean existsByEmail(String email);
}