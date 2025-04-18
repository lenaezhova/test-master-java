package com.testmaster.repository.GroupRepository;

import com.testmaster.model.Group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, GroupRepositoryCustom {

    @Modifying
    @Query("delete from Group g where g.id = :id")
    int delete(@Param("id") Long id);
}
