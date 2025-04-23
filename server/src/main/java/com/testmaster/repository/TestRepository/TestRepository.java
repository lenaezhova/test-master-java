package com.testmaster.repository.TestRepository;

import com.testmaster.model.Test.Test;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>, TestRepositoryCustom {
    @NotNull
    @Query("""
        select test from Test test
        where test.deleted = false
    """)
    List<Test> findAll();

    @NotNull
    @Query("""
        select test from Test test
        where test.id = :id and test.deleted = false
    """)
    Optional<Test> findById(Long id);
}