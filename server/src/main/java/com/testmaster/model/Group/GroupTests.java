package com.testmaster.model.Group;

import com.testmaster.model.Test;
import com.testmasterapi.domain.test.TestGroupsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tests_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupTests {
    @EmbeddedId
    private TestGroupsId id;

    @ManyToOne
    @MapsId("testId")
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;
}