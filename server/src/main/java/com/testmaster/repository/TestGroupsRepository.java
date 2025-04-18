package com.testmaster.repository;

import com.testmaster.model.Group.GroupTests;
import com.testmasterapi.domain.test.TestGroupsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestGroupsRepository extends JpaRepository<GroupTests, TestGroupsId> {
}
