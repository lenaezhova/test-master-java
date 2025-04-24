package com.testmaster.repository.GroupRepository;

import com.testmaster.model.Group;
import com.testmaster.model.TestSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, GroupRepositoryCustom {
    @NotNull
    @Query("""
        select g from Group g
    """)
    List<Group> findAllGroups(Pageable pageable);

    @Query("""
        select count(g) from Group g
    """)
    long countAllGroups();

    @Modifying
    @Query("delete from Group g where g.id = :id")
    int delete(@Param("id") Long id);
}
