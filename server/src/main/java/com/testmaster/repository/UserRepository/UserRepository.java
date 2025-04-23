package com.testmaster.repository.UserRepository;

import com.testmaster.model.User.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    @NotNull
    @Query("select u from User u where u.deleted = false")
    List<User> findUsers(Pageable pageable);

    @Query("select count(u) from User u where u.deleted = false")
    long countUsers();

    @NotNull
    @Query("""
        select user from User user
        where user.id = :id and user.deleted = false
    """)
    Optional<User> findById(@NotNull Long id);

    @Query("""
        select user from User user
        where user.email = :email and user.deleted = false
    """)
    Optional<User> findByEmail(String email);

    @Query("""
        select user from User user
        where user.activationLink = :activationLink and user.deleted = false
    """)
    Optional<User> findByActivationLink(String activationLink);
}