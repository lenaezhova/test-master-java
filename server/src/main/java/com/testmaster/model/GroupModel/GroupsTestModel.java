package com.testmaster.model.GroupModel;

import com.testmaster.model.TestModel.TestGroupsId;
import com.testmaster.model.TestModel.TestModel;
import com.testmaster.model.UserModel.UserGroupsId;
import com.testmaster.model.UserModel.UserModel;
import com.testmaster.model.GroupModel.GroupModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tests_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupsTestModel {
    @EmbeddedId
    private TestGroupsId id;

    @ManyToOne
    @MapsId("testId")
    @JoinColumn(name = "test_id")
    private TestModel test;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private GroupModel group;
}