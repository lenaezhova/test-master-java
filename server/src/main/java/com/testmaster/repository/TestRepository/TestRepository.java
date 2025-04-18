package com.testmaster.repository.TestRepository;

import com.testmaster.model.Test;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>, TestRepositoryCustom {
    @Transactional
    @Modifying
    @Query("delete from Test t where t.id = :id")
    int delete(@Param("id") Long id);
}