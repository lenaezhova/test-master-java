package com.testmaster.repository.TestRepository;

import com.testmaster.model.Test.Test;
import com.testmaster.model.User.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>, TestRepositoryCustom {
    @NotNull
    @Query("""
        select t from Test t
        where (:showDeleted is null or t.deleted = :showDeleted)
    """)
    List<Test> findTests(Boolean showDeleted, Pageable pageable);

    @Query("""
        select count(t) from Test t
        where (:showDeleted is null or t.deleted = :showDeleted)
    """)
    long countTests(Boolean showDeleted);

    @NotNull
    @Query("""
        select test from Test test
        where test.id = :id and test.deleted = false
    """)
    Optional<Test> findById(Long id);
}