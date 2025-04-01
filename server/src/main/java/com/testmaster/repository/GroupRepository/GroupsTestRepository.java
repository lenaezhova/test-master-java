package com.testmaster.repository.GroupRepository;

import com.testmaster.model.GroupModel.GroupModel;
import com.testmaster.model.GroupModel.GroupsTestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsTestRepository extends JpaRepository<GroupsTestModel, Long> {
}
