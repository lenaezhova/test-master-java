package com.testmaster.repository.UserRepository;

import com.testmaster.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    @Query("""
        select user from User user
        where user.deleted = false
    """)
    List<User> findAllUsers();

    @Query("""
        select user from User user
        where user.id = :id and user.deleted = false
    """)
    Optional<User> findUserById(Long id);

    @Query("""
        select user from User user
        where user.email = :email and user.deleted = false
    """)
    Optional<User> findUserByEmail(String email);

    @Query("""
        select user from User user
        where user.activationLink = :activationLink and user.deleted = false
    """)
    Optional<User> findUserByActivationLink(String activationLink);
}