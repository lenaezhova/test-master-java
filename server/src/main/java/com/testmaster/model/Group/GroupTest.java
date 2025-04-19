package com.testmaster.model.Group;

import com.testmaster.model.Test;
import com.testmasterapi.domain.group.GroupTestId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groups_tests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupTest {
    @EmbeddedId
    private GroupTestId id;

    @ManyToOne
    @MapsId("testId")
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;
}