package com.testmaster.repository.GroupRepository;

import com.testmaster.model.GroupModel.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupModel, Long> {
}
