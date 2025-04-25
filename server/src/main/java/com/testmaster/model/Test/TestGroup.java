package com.testmaster.model.Test;

import com.testmaster.model.Group;
import com.testmasterapi.domain.test.TestGroupId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tests_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestGroup {
    @EmbeddedId
    private TestGroupId id;

    @ManyToOne
    @MapsId("testId")
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;
}