package com.testmaster.repository.GroupRepository;

import com.testmaster.model.GroupModel.GroupsTestModel;
import com.testmaster.model.TestModel.TestGroupsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsTestRepository extends JpaRepository<GroupsTestModel, TestGroupsId> {
}
