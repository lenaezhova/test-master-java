package com.testmaster.model.TestModel;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class TestGroupsId implements Serializable {
    private Long testId;
    private Long groupId;
}